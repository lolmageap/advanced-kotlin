package coroutine

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield


fun main() = runBlocking {
    printWithThreadName("START")

    launch {
        newFunction()
    }
    yield()
    printWithThreadName("END")
}

suspend fun newFunction() {
    val one = 1
    val two = 2
    yield()
    printWithThreadName(one + two)
}

fun printWithThreadName(message: Any) {
    println("[${Thread.currentThread().name}] $message")
}