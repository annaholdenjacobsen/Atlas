package atlas.stop

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/stops")
class StopController(private val stopService: StopService) {

    @GetMapping
    fun getAll(): List<StopResponse> = stopService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): StopResponse = stopService.getById(id)

    @GetMapping("/trip/{tripId}")
    fun getAllByTrip(@PathVariable tripId: Long): List<StopResponse> =
        stopService.getAllByTrip(tripId)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateStopRequest): StopResponse =
        stopService.create(request)
}

