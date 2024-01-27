package reflection

import generic.Carp
import generic.GoldFish
import kotlin.reflect.KType

fun main() {
    val one = object : SuperTypeToken<List<GoldFish>>() {}
    val two = object : SuperTypeToken<List<GoldFish>>() {}
    val three = object : SuperTypeToken<List<Carp>>() {}

    require(one.type == two.type)
    require(one.type != three.type)
    require(two.type != three.type)

    //

    val cage = SuperTypeSafeCage()
    cage.put(one, listOf(GoldFish("금붕이"), GoldFish("금붕이2")))
    val carps = cage.get(three)
    println(carps)
}


// TODO : 이 부분이 잘 이해가 가지 않기 떄문에 다시 듣기
// Super Type Token 을 구현한 클래스 가 인스턴스화 되자 마자
// T (type) 을 내부 변수에 저장해 놓는다.
// T <- List<Int> 에서 List<*> 로 제네릭 이 소거 되기 전에 T 를 저장해 놓는다.
abstract class SuperTypeToken<T> {
    val type: KType = this::class.supertypes[0].arguments[0].type!!
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val superTypeToken = other as SuperTypeToken<*>
        return type == other.type
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

@Suppress("UNCHECKED_CAST")
class SuperTypeSafeCage {

    private val animals: MutableMap<SuperTypeToken<*>, Any> = mutableMapOf()

    fun <T : Any> get(token: SuperTypeToken<T>): T? {
        return animals[token] as? T
    }

    fun <T : Any> put(token: SuperTypeToken<T>, animal: T) {
        animals[token] = animal
    }

}
