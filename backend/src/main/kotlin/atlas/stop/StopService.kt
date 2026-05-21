package atlas.stop

import atlas.city.CityRepository
import atlas.trip.TripRepository
import atlas.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StopService(
    private val stopRepository: StopRepository,
    private val tripRepository: TripRepository,
    private val cityRepository: CityRepository,
    private val userRepository: UserRepository,
) {
    fun getById(id: Long, supabaseUserId: String): StopResponse {
        val stop = stopRepository.findByIdOrNull(id)
            ?: throw NoSuchElementException("Stop not found with id=$id")
        assertTripOwnership(stop.trip.id, supabaseUserId)
        return stop.toResponse()
    }

    /** Returns all stops for a trip – only if the trip belongs to the authenticated user. */
    fun getAllByTrip(tripId: Long, supabaseUserId: String): List<StopResponse> {
        assertTripOwnership(tripId, supabaseUserId)
        return stopRepository.findAllByTripId(tripId).map { it.toResponse() }
    }

    @Transactional
    fun create(request: CreateStopRequest, supabaseUserId: String): StopResponse {
        val trip = tripRepository.findByIdOrNull(request.tripId)
            ?: throw NoSuchElementException("Trip not found with id=${request.tripId}")
        val user = requireUser(supabaseUserId)
        if (trip.user.id != user.id) throw AccessDeniedException("You do not have access to this trip")
        val city = cityRepository.findByIdOrNull(request.cityId)
            ?: throw NoSuchElementException("City not found with id=${request.cityId}")
        val stop = Stop(
            trip = trip,
            city = city,
            name = request.name,
            startDate = request.startDate,
            endDate = request.endDate,
        )
        return stopRepository.save(stop).toResponse()
    }

    private fun assertTripOwnership(tripId: Long, supabaseUserId: String) {
        val trip = tripRepository.findByIdOrNull(tripId)
            ?: throw NoSuchElementException("Trip not found with id=$tripId")
        val user = requireUser(supabaseUserId)
        if (trip.user.id != user.id) throw AccessDeniedException("You do not have access to this trip")
    }

    private fun requireUser(supabaseUserId: String) =
        userRepository.findBySupabaseUserId(supabaseUserId)
            ?: throw NoSuchElementException("User profile not found – please complete registration")
}

