package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull


fun main(): Unit = runBlocking {

    val result = withTimeoutOrNull(1000) {
        delay(1000)
        100
    }

    require(result == null)

    val result2 = withTimeout(1000) {
        delay(1000)
        100
    }

}