package reflection

import kotlin.reflect.KClass

fun main() {
    ContainerV1.register(AService::class)
    val aService = ContainerV1.getInstance(AService::class)
    aService.print()
}

object ContainerV1 {
    private val registeredClasses = mutableSetOf<KClass<*>>()
    fun register(vararg kClass: KClass<*>) {
        registeredClasses.addAll(kClass)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T: Any> getInstance(type: KClass<T>): T {
        return registeredClasses.find { it == type }
            ?.let { it.constructors.first().call() as T }
            ?: throw IllegalArgumentException("No registered class for $type")
    }

}
