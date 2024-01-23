fun main() {

}

class Cage {

    private val animals: MutableList<Animal> = mutableListOf()

}

abstract class Animal(
    val name: String,
)

abstract class Fish(name: String): Animal(name)

class GoldFish(name: String): Fish(name)

class Carp(name: String): Fish(name)