package atlas.trip

import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/trips")
class TripController(private val tripService: TripService) {

    /** Returns all trips for the authenticated user. */
    @GetMapping
    fun getAll(token: JwtAuthenticationToken): List<TripResponse> =
        tripService.getAllForUser(token.name)

    /** Returns a trip by ID – only if it belongs to the authenticated user. */
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long, token: JwtAuthenticationToken): TripResponse =
        tripService.getById(id, token.name)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateTripRequest, token: JwtAuthenticationToken): TripResponse =
        tripService.create(request, token.name)
}
