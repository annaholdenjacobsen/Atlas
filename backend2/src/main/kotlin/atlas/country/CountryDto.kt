package atlas.country

import java.time.LocalDateTime

data class CreateCountryRequest(
    val name: String,
    val code: String,
)

data class CountryResponse(
    val id: Long,
    val name: String,
    val code: String,
    val createdAt: LocalDateTime,
)

fun Country.toResponse() = CountryResponse(
    id = id,
    name = name,
    code = code,
    createdAt = createdAt,
)

