package atlas.country

import org.springframework.data.jpa.repository.JpaRepository

interface CountryRepository : JpaRepository<Country, Long> {
    fun existsByCode(code: String): Boolean
}

