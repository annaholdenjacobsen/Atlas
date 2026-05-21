package atlas.country

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CountryRepository : JpaRepository<Country, Long> {
    fun existsByCode(code: String): Boolean
    fun findByNameContainingIgnoreCaseOrderByName(name: String, pageable: Pageable): List<Country>
}
