package tip

@Suppress("DIVISION_BY_ZERO")
fun main() {
    val result: Result<Int> = runCatching { 1 / 0 }
    println(result)
}