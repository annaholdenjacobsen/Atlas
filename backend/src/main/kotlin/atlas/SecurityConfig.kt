package atlas

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth.anyRequest().authenticated()
            }
            // JwtDecoder is auto-configured by Spring Boot from:
            // spring.security.oauth2.resourceserver.jwt.jwk-set-uri
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt {}
            }
            // Return clean JSON for 401 / 403 instead of HTML error pages
            .exceptionHandling { ex ->
                ex.authenticationEntryPoint { _, response, authException ->
                    response.status = 401
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    response.writer.write("""{"message":"${authException.message ?: "Unauthorized"}"}""")
                }
                ex.accessDeniedHandler { _, response, accessDeniedException ->
                    response.status = 403
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    response.writer.write("""{"message":"${accessDeniedException.message ?: "Access denied"}"}""")
                }
            }
        return http.build()
    }
}
