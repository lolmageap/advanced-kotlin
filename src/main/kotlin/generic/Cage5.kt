package generic

fun main() {
    val cage = Cage5(
        mutableListOf(
            Sparrow(),
            Eagle(),
        )
    )

    cage.printAfterSorting()
}


class Cage5 <T>(
    private val animals: MutableList<T> = mutableListOf()
) where T: Animal, T: Comparable<T> {

    fun printAfterSorting() {
        animals.sorted().forEach {
            println(it.name)
        }
    }

    fun getFirst(): T = animals.first()

    fun put(animal: T){
        animals.add(animal)
    }

    fun moveFrom(otherCage: Cage5<T>) {
        animals.addAll(otherCage.animals)
    }

    fun moveTo(otherCage: Cage5<T>) {
        otherCage.animals.addAll(this.animals)
    }
}

fun <T> List<T>.hasIntersection(other: List<T>): Boolean {
    return (this.toSet() intersect other.toSet()).isNotEmpty()
}