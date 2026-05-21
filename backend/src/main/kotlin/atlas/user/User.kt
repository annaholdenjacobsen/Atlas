package atlas.user

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val username: String,

    @Column(name = "first_name", nullable = false)
    val firstName: String,

    @Column(name = "last_name", nullable = false)
    val lastName: String,

    @Column(name = "phone_number", nullable = false)
    val phoneNumber: String,

    @Column(nullable = false, unique = true)
    val email: String,

    /** UUID from Supabase Auth – null for legacy rows created before JWT auth was added. */
    @Column(name = "supabase_user_id", unique = true)
    val supabaseUserId: String? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

