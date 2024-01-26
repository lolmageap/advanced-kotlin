package dsl

import java.time.LocalDate


fun main() {
    var point = Point(10, -20)
    println(point.zeroPointerSymmetry())

    //

    println(-point)
    println(++point)

    LocalDate.of(2021, 1, 1) + Days(10)
    LocalDate.of(2021, 1, 1) + 3.d
}

data class Point(
    val x: Int,
    val y: Int,
) {
    fun zeroPointerSymmetry() = Point( -x, -y)

    operator fun unaryMinus() = Point(-x, -y)

    operator fun inc() = Point(x + 1, y + 1)
}

val Int.d
    get() = Days(this.toLong())

data class Days(
    val days: Long,
)

operator fun LocalDate.plus(days: Days): LocalDate = this.plusDays(days.days)