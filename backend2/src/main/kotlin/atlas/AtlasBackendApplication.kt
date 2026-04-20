package atlas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AtlasBackendApplication

fun main(args: Array<String>) {
	runApplication<atlas.AtlasBackendApplication>(*args)
}
