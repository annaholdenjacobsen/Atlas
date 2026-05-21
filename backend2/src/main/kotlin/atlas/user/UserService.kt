package atlas.user

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(private val userRepository: UserRepository) {

    fun getAll(): List<UserResponse> =
        userRepository.findAll().map { it.toResponse() }

    fun getById(id: Long): UserResponse =
        userRepository.findByIdOrNull(id)?.toResponse()
            ?: throw NoSuchElementException("User not found with id=$id")

    @Transactional
    fun create(request: CreateUserRequest): UserResponse {
        require(!userRepository.existsByUsername(request.username)) {
            "Username '${request.username}' is already taken"
        }
        require(!userRepository.existsByEmail(request.email)) {
            "Email '${request.email}' is already registered"
        }
        val user = User(
            username = request.username,
            firstName = request.firstName,
            lastName = request.lastName,
            phoneNumber = request.phoneNumber,
            email = request.email,
        )
        return userRepository.save(user).toResponse()
    }
}

