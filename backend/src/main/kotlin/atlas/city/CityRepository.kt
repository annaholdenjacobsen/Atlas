package atlas.city

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CityRepository : JpaRepository<City, Long> {
    fun findByNameContainingIgnoreCaseOrderByName(name: String, pageable: Pageable): List<City>
    fun findByNameContainingIgnoreCaseAndCountryIgnoreCaseOrderByName(
        name: String, country: String, pageable: Pageable
    ): List<City>
}
