package reflection

import org.reflections.Reflections
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.cast

class DI

fun main() {
//    ContainerV2.register(
//        AService::class,
//        BService::class,
//    )
//
//    val bService = ContainerV2.getInstance(BService::class)
//    bService.print()

    //
    start(DI::class)

    val bService = ContainerV2.getInstance(BService::class)
    bService.print()
}

object ContainerV2 {
    private val registeredClasses = mutableSetOf<KClass<*>>()
    private val cachedInstances = mutableMapOf<KClass<*>, Any>()

    fun register(vararg kClass: KClass<*>) {
        registeredClasses.addAll(kClass)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getInstance(type: KClass<T>): T {
        if (type in cachedInstances) {
            return type.cast(cachedInstances[type])
        }

        val instance = registeredClasses.find { it == type }
            ?.let {
                instantiate(it) as T
            }
            ?: throw IllegalArgumentException("No registered class for $type")

        return instance.also {
            cachedInstances[type] = it
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> instantiate(type: KClass<T>): T {
        val constructor = findUsableConstructor(type)
        val params = constructor.parameters.map {
            getInstance(it.type.classifier as KClass<*>)
        }.toTypedArray()

        return constructor.call(*params) as T
    }

    // clazz 의 constructor 들 중에 사용할 수 있는 constructor 를 찾는다.
    // constructor 에 넣어야 하는 타입들 이 모두 등록 되어 있는 경우 (container 에서 관리 하는 경우)
    private fun findUsableConstructor(clazz: KClass<*>): KFunction<*> {
        return clazz.constructors.firstOrNull { constructor ->
            constructor.parameters.isAllRegistered
        } ?: throw IllegalArgumentException("$clazz 는 사용 할 수 없는 class 입니다.")
    }

    private val List<KParameter>.isAllRegistered: Boolean
        get() = this.all { it.type.classifier in registeredClasses }
}

fun start(kClass: KClass<*>) {
    val reflections = Reflections(kClass.packageName)
    val jClasses = reflections.getTypesAnnotatedWith(MyClass::class.java)

    jClasses.forEach {
        ContainerV2.register(it.kotlin)
    }
}

val KClass<*>.packageName: String
    get() = this.qualifiedName?.substringBeforeLast(".") ?: ""
