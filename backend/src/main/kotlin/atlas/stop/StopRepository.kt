package atlas.stop

import org.springframework.data.jpa.repository.JpaRepository

interface StopRepository : JpaRepository<Stop, Long> {
    fun findAllByTripId(tripId: Long): List<Stop>
}

