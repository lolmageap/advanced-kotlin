package reflection

import generic.Animal
import generic.GoldFish
import kotlin.reflect.KClass
import kotlin.reflect.cast

fun main() {
    val cage = TypeSafeCage()
    cage.put(GoldFish::class, GoldFish("금붕이"))
    val goldFish = cage.get(GoldFish::class)
    println(goldFish.name)

    //

    val cage2 = TypeSafeCage()
    cage2.put(GoldFish("금붕이"))
    val goldFish2 = cage2.get<GoldFish>()
    val goldFish3: GoldFish = cage2.get()

    println(goldFish2.name)
    println(goldFish3.name)
}

class TypeSafeCage {

    private val animals: MutableMap<KClass<*>, Animal> = mutableMapOf()

    fun <T : Animal> get(type: KClass<T>): T {
        return type.cast(animals[type])
    }

    fun <T : Animal> put(type: KClass<T>, animal: T) {
        animals[type] = type.cast(animal)
    }

    // type 안전 이종 container 패턴
    inline fun <reified T: Animal> get(): T {
        return get(T::class)
    }

    inline fun <reified T: Animal> put(animal: T) {
        put(T::class, animal)
    }

}
