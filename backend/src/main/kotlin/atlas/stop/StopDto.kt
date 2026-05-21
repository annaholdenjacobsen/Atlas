package atlas.stop

import atlas.city.CityResponse
import atlas.city.toResponse
import atlas.trip.TripResponse
import atlas.trip.toResponse
import java.time.LocalDate
import java.time.LocalDateTime

data class CreateStopRequest(
    val tripId: Long,
    val cityId: Long,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
)

data class StopResponse(
    val id: Long,
    val trip: TripResponse,
    val city: CityResponse,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val createdAt: LocalDateTime,
)

fun Stop.toResponse(): StopResponse = StopResponse(
    id = id,
    trip = trip.toResponse(),
    city = city.toResponse(),
    name = name,
    startDate = startDate,
    endDate = endDate,
    createdAt = createdAt,
)

