package atlas.country

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/countries")
class CountryController(private val countryService: CountryService) {

    @GetMapping
    fun getAll(): List<CountryResponse> = countryService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): CountryResponse = countryService.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateCountryRequest): CountryResponse =
        countryService.create(request)
}

