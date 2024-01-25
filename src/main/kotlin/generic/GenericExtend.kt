package generic


fun main() {

}

open class CageV1<T: Animal> {
    open fun addAnimal(animal: T) {
        println("addAnimal: ${animal.name}")
    }
}

class CageV2<T: Animal>: CageV1<T>() {
    override fun addAnimal(animal: T) {
        super.addAnimal(animal)
    }
}

class GoldFishCageV2: CageV1<GoldFish>() {
    override fun addAnimal(animal: GoldFish) {
        super.addAnimal(animal)
    }
}
