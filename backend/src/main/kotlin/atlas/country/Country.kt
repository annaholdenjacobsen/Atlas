package atlas.country

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "countries")
class Country(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false, unique = true)
    val code: String,
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
