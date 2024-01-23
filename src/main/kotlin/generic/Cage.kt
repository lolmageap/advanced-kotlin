package generic


fun main() {
    val cage = Cage()
    val goldFish = GoldFish("GoldFish")

    cage.put(goldFish)
    val newGoldFish: GoldFish = cage.getFirst() as? GoldFish ?: GoldFish("new GoldFish")
}

class Cage {

    private val animals: MutableList<Animal> = mutableListOf()

    fun getFirst(): Animal =
        animals.first()

    fun put(animal: Animal) =
        animals.add(animal)

    fun moveFrom(cage: Cage) =
        animals.addAll(cage.animals)

}
