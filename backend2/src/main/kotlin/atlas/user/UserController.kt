package atlas.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAll(): List<UserResponse> = userService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): UserResponse = userService.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateUserRequest): UserResponse =
        userService.create(request)
}

