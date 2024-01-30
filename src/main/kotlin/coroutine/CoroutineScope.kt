package coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis


fun main(): Unit = measureTimeMillis {
    runBlocking {
        printWithThreadName("코루틴 시작")
        printWithThreadName(calculate())
        printWithThreadName("코루틴 종료")
    }
}.let {
    printWithThreadName(it)
}

suspend fun calculate(): Int = coroutineScope {
    val num1 = async {
        delay(1000)
        10
    }

    val num2 = async {
        delay(1000)
        20
    }

    num1.await() + num2.await()
}