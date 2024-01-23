package generic

fun main() {
    val cage = Cage4<Animal>()
    val fishCage = Cage3<GoldFish>()

    cage.put(fishCage.getFirst())
}

class Cage4 <in T> {

    private val animals: MutableList<T> = mutableListOf()

    fun put(animal: T){
        animals.add(animal)
    }

    fun putAll(animals: List<T>) {
        this.animals.addAll(animals)
    }
}

