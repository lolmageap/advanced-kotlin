package coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = measureTimeMillis {
    runBlocking {
        val async1 = async {
            apiCall1()
        }

        val async2 = async {
            apiCall2(async1.await())
        }

        printWithThreadName(async2.await())
    }
}.let {
    printWithThreadName("TIME: $it")
}


suspend fun apiCall1(): Int {
    delay(1_000)
    return 1
}

suspend fun apiCall2(number: Int): Int {
    delay(1_000)
    return number + 2
}