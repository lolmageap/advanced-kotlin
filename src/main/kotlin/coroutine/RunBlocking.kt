package coroutine

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {
    example4()
}

fun example4() =
    measureTimeMillis {
        runBlocking {
            val job1 = launch {
                delay(1_000)
                printWithThreadName("JOB 1")
            }

            // job 에 join 을 걸면 해당 job 이 끝날 때까지 다음 job 이 기다린 다.
            job1.join()

            val job2 = launch {
                delay(1_000)
                printWithThreadName("JOB 2")
            }
        }
    }.let {
        printWithThreadName("TIME: $it")
    }

fun example3() = runBlocking {
    val job = launch {
        (1..5).forEach {
            printWithThreadName(it)
            delay(500)
        }
    }

    delay(1_000)
    job.cancel()
}

fun example2() = runBlocking {
    val job = launch(start = CoroutineStart.LAZY) {
        printWithThreadName("LAUNCH START")
    }

    delay(1_000)
    job.start()
    printWithThreadName("END")
}

fun example1() {
    runBlocking {
        printWithThreadName("START")

        launch {
            printWithThreadName("LAUNCH START")
            delay(2_000)
            printWithThreadName("LAUNCH END")
        }
    }
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