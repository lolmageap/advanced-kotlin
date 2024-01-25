package generic


fun main() {
    val cage = CageShadow<GoldFish>()
    cage.addAnimal(GoldFish("GoldFish"))
    cage.addAnimal(Sparrow()) // Sparrow is not GoldFish Type!!!!!!
}

class CageShadow<T: Animal> {
    fun <T: Animal> addAnimal(animal: T) {
        println("addAnimal: ${animal.name}")
    }

}