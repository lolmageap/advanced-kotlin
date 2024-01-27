package dsl

fun main() {
    val yml = dockerCompose {
        version { 3 }
        service("db") {
            image { "postgresql:latest" }
            env("POSTGRES_USER" - "postgres")
            env("POSTGRES_PASSWORD" - "postgres")
            port(5433, 5432)
        }
    }

    println(yml.render())
}

fun dockerCompose(init: DockerCompose.() -> Unit): DockerCompose {
    val dockerCompose = DockerCompose()
    dockerCompose.init()
    return dockerCompose
}

@YmlMarker
class DockerCompose {
    private var version: Int by onceNotNull()
    private val services = mutableListOf<Service>()

    fun version(init: () -> Int) {
        version = init()
    }

    fun service(name: String, init: Service.() -> Unit) {
        val service = Service(name)
        service.init()
        services.add(service)
    }

    fun render(ident: String = "  "): String =
        StringBuilder().apply {
            appendNew("version: '$version'")
            appendNew("services:")
            appendNew(
                services.joinToString("\n") {
                    it.render(ident)
                }.addIdent(ident, 1)
            )
        }.toString()

}

