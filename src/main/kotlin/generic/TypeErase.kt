package generic

fun main() {
    val numbers = listOf(1, 2.0, 3.1f, 4L)

    val long = numbers.filterIsInstance<Long>()
    require(long.size == 1)
}

inline fun <reified T> T.toSuperString() {
    println("${T::class.java.name}: $this")
}

inline fun <reified T> List<*>.hasAnyInstanceOf(): Boolean {
    return this.any { it is T }
}
