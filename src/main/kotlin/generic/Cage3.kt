package generic

fun main() {
    val cage: Cage3<Animal> = Cage3<GoldFish>()
    val all = cage.getAll()

}

class Cage3 <out T> {

    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T = animals.first()

    fun getAll(): List<T> = animals
}
