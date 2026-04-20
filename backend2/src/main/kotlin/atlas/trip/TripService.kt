package atlas.trip

import com.example.backend2.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TripService(
    private val tripRepository: TripRepository,
    private val userRepository: UserRepository,
) {
    fun getAll(): List<TripResponse> =
        tripRepository.findAll().map { it.toResponse() }

    fun getById(id: Long): TripResponse =
        tripRepository.findByIdOrNull(id)?.toResponse()
            ?: throw NoSuchElementException("Trip not found with id=$id")

    fun getAllByUser(userId: Long): List<TripResponse> =
        tripRepository.findAllByUserId(userId).map { it.toResponse() }

    @Transactional
    fun create(request: CreateTripRequest): TripResponse {
        val user = userRepository.findByIdOrNull(request.userId)
            ?: throw NoSuchElementException("User not found with id=${request.userId}")
        val trip = Trip(
            user = user,
            name = request.name,
            startDate = request.startDate,
            endDate = request.endDate,
        )
        return tripRepository.save(trip).toResponse()
    }
}

