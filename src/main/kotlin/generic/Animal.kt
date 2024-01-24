package generic


abstract class Animal(
    val name: String,
)

abstract class Fish(name: String): Animal(name)

class GoldFish(name: String): Fish(name)

class Carp(name: String): Fish(name)

abstract class Bird(
    name: String,
    private val size: Int,
): Animal(name), Comparable<Bird> {
    override fun compareTo(other: Bird): Int {
        return size.compareTo(other.size)
    }
}

class Sparrow: Bird("참새", 100)

class Eagle: Bird("독수리", 500)