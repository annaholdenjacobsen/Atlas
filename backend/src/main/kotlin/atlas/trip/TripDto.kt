package atlas.trip

import atlas.user.UserResponse
import atlas.user.toResponse
import java.time.LocalDate
import java.time.LocalDateTime

data class CreateTripRequest(
    val userId: Long,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
)

data class TripResponse(
    val id: Long,
    val user: UserResponse,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val createdAt: LocalDateTime,
)

fun Trip.toResponse(): TripResponse = TripResponse(
    id = id,
    user = user.toResponse(),
    name = name,
    startDate = startDate,
    endDate = endDate,
    createdAt = createdAt,
)

