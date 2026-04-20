package atlas.stop

import com.example.backend2.city.CityRepository
import com.example.backend2.trip.TripRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StopService(
    private val stopRepository: StopRepository,
    private val tripRepository: TripRepository,
    private val cityRepository: CityRepository,
) {
    fun getAll(): List<StopResponse> =
        stopRepository.findAll().map { it.toResponse() }

    fun getById(id: Long): StopResponse =
        stopRepository.findByIdOrNull(id)?.toResponse()
            ?: throw NoSuchElementException("Stop not found with id=$id")

    fun getAllByTrip(tripId: Long): List<StopResponse> =
        stopRepository.findAllByTripId(tripId).map { it.toResponse() }

    @Transactional
    fun create(request: CreateStopRequest): StopResponse {
        val trip = tripRepository.findByIdOrNull(request.tripId)
            ?: throw NoSuchElementException("Trip not found with id=${request.tripId}")
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
}

