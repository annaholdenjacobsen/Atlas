package atlas.city

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cities")
class CityController(private val cityService: CityService) {

    @GetMapping
    fun getAll(): List<CityResponse> = cityService.getAll()

    @GetMapping("/search")
    fun search(
        @RequestParam q: String,
        @RequestParam(required = false) country: String?,
        @RequestParam(defaultValue = "20") limit: Int,
    ): List<CityResponse> = cityService.search(q, country, limit)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): CityResponse = cityService.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateCityRequest): CityResponse =
        cityService.create(request)
}
