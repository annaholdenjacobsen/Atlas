package atlas

import atlas.city.City
import atlas.city.CityRepository
import atlas.country.Country
import atlas.country.CountryRepository
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.slf4j.LoggerFactory
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

/**
 * One-time data seeder for countries and cities.
 *
 * This script is NOT registered as a Spring bean and will NOT run on application startup.
 * It is kept here for documentation/reference purposes, in case the database needs to be
 * re-seeded from scratch.
 *
 * To re-run manually, temporarily add @Component and @ApplicationRunner back,
 * or call the run() method directly in a test/migration context.
 *
 * Uses two free public APIs (no auth required):
 *  - https://restcountries.com  → all countries with ISO-2 codes
 *  - https://countriesnow.space → all countries with their cities
 */
class DataSeeder(
    private val countryRepository: CountryRepository,
    private val cityRepository: CityRepository,
    restClientBuilder: RestClient.Builder,
) {

    private val log = LoggerFactory.getLogger(DataSeeder::class.java)
    private val restClient = restClientBuilder.build()

    // ── DTOs ─────────────────────────────────────────────────────────────────

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class RestCountryDto(val name: RestCountryName, val cca2: String)

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class RestCountryName(val common: String)

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class CountriesNowResponse(val error: Boolean, val data: List<CountriesNowItem>)

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class CountriesNowItem(val country: String, val cities: List<String>)

    // ── Seeder ────────────────────────────────────────────────────────────────

    fun run() {
        if (countryRepository.count() > 0) {
            log.info("Countries already seeded – skipping DataSeeder")
            return
        }

        log.info("Seeding countries and cities from public APIs …")

        // 1. Fetch ISO-2 codes from RestCountries
        val isoMap: Map<String, String> = try {
            restClient.get()
                .uri("https://restcountries.com/v3.1/all?fields=name,cca2")
                .retrieve()
                .body<Array<RestCountryDto>>()
                ?.associate { it.name.common to it.cca2 }
                ?: emptyMap()
        } catch (e: Exception) {
            log.warn("Could not fetch ISO codes from restcountries.com: ${e.message}")
            emptyMap()
        }

        // 2. Fetch country → [cities] list from CountriesNow
        val countriesNow: CountriesNowResponse? = try {
            restClient.get()
                .uri("https://countriesnow.space/api/v0.1/countries")
                .retrieve()
                .body<CountriesNowResponse>()
        } catch (e: Exception) {
            log.error("Could not fetch countries/cities from countriesnow.space: ${e.message}")
            null
        }

        if (countriesNow == null || countriesNow.error) {
            log.error("CountriesNow response was empty or had errors – aborting seed")
            return
        }

        // 3. Save countries
        val seenCodes = mutableSetOf<String>()
        val countries = countriesNow.data.mapNotNull { item ->
            val code = isoMap[item.country]
                ?: item.country.take(2).uppercase()   // fallback: first 2 letters
            if (!seenCodes.add(code)) return@mapNotNull null   // skip duplicates
            Country(name = item.country, code = code)
        }
        countryRepository.saveAll(countries)
        log.info("Saved ${countries.size} countries")

        // 4. Save cities in chunks to avoid large transactions
        val cities = countriesNow.data.flatMap { item ->
            item.cities.map { cityName -> City(name = cityName, country = item.country) }
        }
        var savedCount = 0
        cities.chunked(500).forEach { chunk ->
            cityRepository.saveAll(chunk)
            savedCount += chunk.size
        }
        log.info("Saved $savedCount cities")
    }
}

