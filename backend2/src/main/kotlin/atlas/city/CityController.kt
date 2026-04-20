package atlas.city

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cities")
class CityController(private val cityService: CityService) {

    @GetMapping
    fun getAll(): List<CityResponse> = cityService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): CityResponse = cityService.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateCityRequest): CityResponse =
        cityService.create(request)
}

