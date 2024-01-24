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

// Generic Type Parameter 에 Any 를 상속 받으면 Null 을 허용 하지 않는다.
class Cage2 <T: Any> {

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
