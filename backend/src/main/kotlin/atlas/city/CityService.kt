package atlas.city

import org.springframework.data.domain.PageRequest
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

    fun search(query: String, country: String? = null, limit: Int = 20): List<CityResponse> {
        val pageable = PageRequest.of(0, limit)
        return if (country != null) {
            cityRepository.findByNameContainingIgnoreCaseAndCountryIgnoreCaseOrderByName(query, country, pageable)
        } else {
            cityRepository.findByNameContainingIgnoreCaseOrderByName(query, pageable)
        }.map { it.toResponse() }
    }

    @Transactional
    fun create(request: CreateCityRequest): CityResponse {
        val city = City(name = request.name, country = request.country)
        return cityRepository.save(city).toResponse()
    }
}
