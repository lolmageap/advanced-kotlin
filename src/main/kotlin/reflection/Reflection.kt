package reflection

import generic.GoldFish
import kotlin.reflect.*
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.createType
import kotlin.reflect.full.hasAnnotation

fun main() {
    val kClass: KClass<Reflection> = Reflection::class
    kClass.java
    kClass.java.kotlin

    val obj = Reflection()
    val kClass2: KClass<out Reflection> = obj::class

    val kClass3: KClass<out Any> = Class.forName("reflection.Reflection").kotlin

    val kType: KType = GoldFish::class.createType()

    val goldFish = GoldFish("금붕이")
    goldFish::class.members.first() { it.name == "swim" }.call(goldFish)

    executeAll(Reflection())
}

@Executable
class Reflection {

    fun a() {
        println("a 입니다.")
    }

    fun b(): Int {
        println("b 입니다.")
        return 0
    }

    fun c(str: String) {
        println("c 입니다.")
    }
}

fun executeAll(obj: Any) {
    val kClass = obj::class
    if (!kClass.hasAnnotation<Executable>()) {
        return
    }
    val callableFunction = kClass.members.filterIsInstance<KFunction<*>>()
        .filter { it.returnType == Unit::class.createType() }
        .filter { it.parameters.size == 1 && it.parameters[0] == kClass.createType() }

    callableFunction.forEach { function ->
        function.call(obj)
    }
}

fun castByGoldFish(obj: Any) = GoldFish::class.cast(obj)

annotation class Executable

