package atlas.stop

import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/stops")
class StopController(private val stopService: StopService) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long, token: JwtAuthenticationToken): StopResponse =
        stopService.getById(id, token.name)

    /** Returns all stops for a trip – only if the trip belongs to the authenticated user. */
    @GetMapping("/trip/{tripId}")
    fun getAllByTrip(@PathVariable tripId: Long, token: JwtAuthenticationToken): List<StopResponse> =
        stopService.getAllByTrip(tripId, token.name)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateStopRequest, token: JwtAuthenticationToken): StopResponse =
        stopService.create(request, token.name)
}
