package atlas.trip

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/trips")
class TripController(private val tripService: TripService) {

    @GetMapping
    fun getAll(): List<TripResponse> = tripService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): TripResponse = tripService.getById(id)

    @GetMapping("/user/{userId}")
    fun getAllByUser(@PathVariable userId: Long): List<TripResponse> =
        tripService.getAllByUser(userId)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateTripRequest): TripResponse =
        tripService.create(request)
}

