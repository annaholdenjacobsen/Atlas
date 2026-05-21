package atlas.trip

import org.springframework.data.jpa.repository.JpaRepository

interface TripRepository : JpaRepository<Trip, Long> {
    fun findAllByUserId(userId: Long): List<Trip>
}

