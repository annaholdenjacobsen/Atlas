package atlas.city

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CityService(private val cityRepository: CityRepository) {

    fun getAll(): List<CityResponse> =
        cityRepository.findAll().map { it.toResponse() }

    fun getById(id: Long): CityResponse =
        cityRepository.findByIdOrNull(id)?.toResponse()
            ?: throw NoSuchElementException("City not found with id=$id")

    @Transactional
    fun create(request: CreateCityRequest): CityResponse {
        val city = City(name = request.name, country = request.country)
        return cityRepository.save(city).toResponse()
    }
}

