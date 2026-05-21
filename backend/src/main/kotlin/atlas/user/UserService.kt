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

    fun getBySupabaseUserId(supabaseUserId: String): UserResponse =
        userRepository.findBySupabaseUserId(supabaseUserId)?.toResponse()
            ?: throw NoSuchElementException("User profile not found – please complete registration")

    /**
     * Internal lookup that returns the entity (not DTO) for use in other services.
     */
    fun requireUserEntity(supabaseUserId: String): User =
        userRepository.findBySupabaseUserId(supabaseUserId)
            ?: throw NoSuchElementException("User profile not found – please complete registration")

    @Transactional
    fun create(request: CreateUserRequest, supabaseUserId: String, email: String): UserResponse {
        require(!userRepository.existsBySupabaseUserId(supabaseUserId)) {
            "A profile already exists for this account"
        }
        require(!userRepository.existsByUsername(request.username)) {
            "Username '${request.username}' is already taken"
        }
        require(!userRepository.existsByEmail(email)) {
            "Email '$email' is already registered"
        }
        val user = User(
            supabaseUserId = supabaseUserId,
            username = request.username,
            firstName = request.firstName,
            lastName = request.lastName,
            phoneNumber = request.phoneNumber,
            email = email,
        )
        return userRepository.save(user).toResponse()
    }
}
