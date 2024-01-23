package generic

fun main() {
    val cage = Cage2<Carp>()
    val carp = Carp("Carp")
    cage.put(carp)

    val newCarp: Carp = cage.getFirst()

    //

    val fishCage = Cage2<Fish>()
    val goldFishCage = Cage2<GoldFish>()

    goldFishCage.moveTo(fishCage)

    //

    val fish: Cage2<out Fish> = Cage2<GoldFish>()
}

class Cage2 <T> {

    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T =
        animals.first()

    fun put(animal: T) =
        animals.add(animal)

    fun moveFrom(otherCage: Cage2<out T>) {
        animals.addAll(otherCage.animals)
    }

    fun moveTo(otherCage: Cage2<in T>) {
        otherCage.animals.addAll(this.animals)
    }

}
