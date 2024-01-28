package coroutine

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch(Dispatchers.Default) {
        var i = 1
        var currentTime = System.currentTimeMillis()
        while (i <= 5) {
            if (currentTime <= System.currentTimeMillis()) {
                printWithThreadName("${i++}번째 출력")
                currentTime += 1_000
            }

            if (!isActive) {
                throw CancellationException()
            }
        }
    }

    delay(100)
    job.cancel()
}

