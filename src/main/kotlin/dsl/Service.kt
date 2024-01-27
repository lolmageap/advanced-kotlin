package dsl


@YmlMarker
class Service(private val name: String) {
    private var image: String by onceNotNull()
    private val environments = mutableListOf<Environment>()
    private lateinit var portRule: PortRule

    fun image(init: () -> String) {
        image = init()
    }

    fun env(environment: Environment) {
        environments.add(environment)
    }

    fun port(host: Int, container: Int) {
        portRule = PortRule(host, container)
    }

    fun render(ident: String): String =
        StringBuilder().apply {
            appendNew("$name:")
            appendNew("image: '$image'", ident, 1)
            appendNew("environment:")
            appendNew(
                environments.joinToString("\n") {
                    "- ${it.key}: '${it.value}'"
                }.addIdent(ident, 1)
            )
            appendNew("port:")
            appendNew(
                "- '${portRule.host}: ${portRule.container}'"
                    .addIdent(ident, 1)
            )
        }.toString()
}