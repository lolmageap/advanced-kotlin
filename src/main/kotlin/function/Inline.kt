package function

fun main() {
    repeat(10) { println("hello world") }

    iterate(10) {  num ->
//        if (num == 3) {
//            return
//        }
        println("hello world")
    }
}

inline fun iterate(times: Int, crossinline action: (Int) -> Unit) {
    for (index in 0 .. times) {
        action(index)
    }
}

inline fun repeat(
    times: Int,
    action: () -> Unit,
) {
    for (index in 0 .. times) {
        action()
    }
}