package atlas.city

import java.time.LocalDateTime

data class CreateCityRequest(
    val name: String,
    val country: String,
)

data class CityResponse(
    val id: Long,
    val name: String,
    val country: String,
    val createdAt: LocalDateTime,
)

fun City.toResponse() = CityResponse(
    id = id,
    name = name,
    country = country,
    createdAt = createdAt,
)

