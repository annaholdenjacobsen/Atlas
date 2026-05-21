package atlas.user

import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    /** Returns the profile of the currently authenticated user. */
    @GetMapping("/me")
    fun getMe(token: JwtAuthenticationToken): UserResponse =
        userService.getBySupabaseUserId(token.name)

    /**
     * Registers a new user profile for the authenticated Supabase account.
     * Email and Supabase user ID are taken from the JWT – not from the request body.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateUserRequest, token: JwtAuthenticationToken): UserResponse {
        val jwt = token.token as Jwt
        val supabaseUserId = jwt.subject
        val email = jwt.getClaimAsString("email")
            ?: throw IllegalArgumentException("Email claim missing from token")
        return userService.create(request, supabaseUserId, email)
    }
}
