package atlas.trip

import atlas.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TripService(
    private val tripRepository: TripRepository,
    private val userRepository: UserRepository,
) {
    /** Returns all trips that belong to the authenticated user. */
    fun getAllForUser(supabaseUserId: String): List<TripResponse> {
        val user = requireUser(supabaseUserId)
        return tripRepository.findAllByUserId(user.id).map { it.toResponse() }
    }

    /** Returns a single trip, asserting it belongs to the authenticated user. */
    fun getById(id: Long, supabaseUserId: String): TripResponse {
        val trip = tripRepository.findByIdOrNull(id)
            ?: throw NoSuchElementException("Trip not found with id=$id")
        val user = requireUser(supabaseUserId)
        if (trip.user.id != user.id) throw AccessDeniedException("You do not have access to this trip")
        return trip.toResponse()
    }

    @Transactional
    fun create(request: CreateTripRequest, supabaseUserId: String): TripResponse {
        val user = requireUser(supabaseUserId)
        val trip = Trip(
            user = user,
            name = request.name,
            startDate = request.startDate,
            endDate = request.endDate,
        )
        return tripRepository.save(trip).toResponse()
    }

    /** Returns the trip entity and asserts ownership – used by other services. */
    fun requireTrip(tripId: Long, supabaseUserId: String): Trip {
        val trip = tripRepository.findByIdOrNull(tripId)
            ?: throw NoSuchElementException("Trip not found with id=$tripId")
        val user = requireUser(supabaseUserId)
        if (trip.user.id != user.id) throw AccessDeniedException("You do not have access to this trip")
        return trip
    }

    private fun requireUser(supabaseUserId: String) =
        userRepository.findBySupabaseUserId(supabaseUserId)
            ?: throw NoSuchElementException("User profile not found – please complete registration")
}

