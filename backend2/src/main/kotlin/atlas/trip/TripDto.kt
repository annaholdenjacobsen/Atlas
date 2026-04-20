package atlas.trip

import com.example.backend2.user.UserResponse
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

fun Trip.toResponse() = TripResponse(
    id = id,
    user = user.toResponse(),
    name = name,
    startDate = startDate,
    endDate = endDate,
    createdAt = createdAt,
)

