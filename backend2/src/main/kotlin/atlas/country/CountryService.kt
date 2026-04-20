package atlas.country

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CountryService(private val countryRepository: CountryRepository) {

    fun getAll(): List<CountryResponse> =
        countryRepository.findAll().map { it.toResponse() }

    fun getById(id: Long): CountryResponse =
        countryRepository.findByIdOrNull(id)?.toResponse()
            ?: throw NoSuchElementException("Country not found with id=$id")

    @Transactional
    fun create(request: CreateCountryRequest): CountryResponse {
        require(!countryRepository.existsByCode(request.code)) {
            "Country with code '${request.code}' already exists"
        }
        val country = Country(name = request.name, code = request.code)
        return countryRepository.save(country).toResponse()
    }
}

