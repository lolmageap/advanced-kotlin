package coroutine

import kotlinx.coroutines.*
import java.util.concurrent.Executors

fun main(): Unit = runBlocking {
    CoroutineName("나만의 코루틴")
    val threadPool = Executors.newSingleThreadExecutor()
    val dispatcher = threadPool.asCoroutineDispatcher()
    CoroutineScope(dispatcher).launch {
        printWithThreadName("코루틴 시작")
        delay(1000)
        printWithThreadName("코루틴 종료")
    }
}

class AsyncLogic {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    fun doSomething() {
        scope.launch {
            printWithThreadName("doSomething")
            coroutineContext + CoroutineName("이름")
        }
    }

    fun destroy() {
        scope.cancel()
    }
}