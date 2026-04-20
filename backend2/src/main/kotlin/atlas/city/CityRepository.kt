package atlas.city

import org.springframework.data.jpa.repository.JpaRepository

interface CityRepository : JpaRepository<City, Long>

