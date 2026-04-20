package atlas.user

import java.time.LocalDateTime

data class CreateUserRequest(
    val username: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
)

data class UserResponse(
    val id: Long,
    val username: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val createdAt: LocalDateTime,
)

fun User.toResponse() = UserResponse(
    id = id,
    username = username,
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber,
    email = email,
    createdAt = createdAt,
)

