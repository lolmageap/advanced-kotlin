package coroutine

import kotlinx.coroutines.*
import java.lang.IllegalArgumentException

/** CoroutineExceptionHandler 은 launch 에만 적용이 가능 하다.
 * 부모 코루틴(현재 코루틴 이 최상단 이 아니 라면...) 이 존재 하면 동작 하지 않는다.
 * 발생한 예외가 CancellationException 인 경우 취소로 간주 하고 예외가 발생 하지 않고 코루틴 을 종료 한다. (부모 코루틴 에게 전파 x)
 **/
suspend fun main(): Unit = coroutineScope {

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        printWithThreadName("CoroutineExceptionHandler: $throwable")
    }

    CoroutineScope(Dispatchers.Default).launch(exceptionHandler) {
        throw IllegalArgumentException()
    }

    delay(1_000)
}

