package coroutine

import kotlinx.coroutines.*
import java.lang.IllegalArgumentException

/**
 *  coroutine builder 안에 새로운 coroutine builder 를 만들 수 있다.
 *  이렇게 만들 어진 coroutine 은 부모 coroutine 과 독립적 으로 실행 된다.
 *  자식 coroutine 에서 async 를 사용 하면 에러가 발생 하면 job.await() 을 하지 않는 다면 에러 처리가 되지 않는다.
 *  SupervisorJob() 을 사용 하면 자식 coroutine 에서 에러가 발생 하더 라도 부모 coroutine 은 에러 처리가 되지 않는다.
 */
suspend fun main(): Unit = coroutineScope {

    launch(SupervisorJob()) {
        val job = async { throw IllegalArgumentException() }
    }

//    val job0 = CoroutineScope(Dispatchers.Default).async {
//        throw IllegalArgumentException()
//    }

    delay(1_000)
//    val job1 = CoroutineScope(Dispatchers.Default).launch {
//        delay(1_000)
//        printWithThreadName("Job 1")
//    }
//
//    val job2 = CoroutineScope(Dispatchers.Default).launch {
//        delay(1_000)
//        printWithThreadName("Job 2")
//    }
}

