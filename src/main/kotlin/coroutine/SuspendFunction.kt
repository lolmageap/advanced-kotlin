package coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.future.await
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CompletableFuture


fun main(): Unit = runBlocking {

    launch {
        a()
        b()
    }

    launch {
        c()
    }

    //

    val result1 = call1()

    val result2 = call2(result1)

    printWithThreadName(result2)

}

suspend fun a() {
    printWithThreadName("a")
}

suspend fun b() {
    printWithThreadName("b")
}

suspend fun c() {
    printWithThreadName("b")
}

suspend fun call1(): Int = coroutineScope {
    async {
        Thread.sleep(1000)
        100
    }.await()
}

suspend fun call2(num: Int): Int = coroutineScope {
    CompletableFuture.supplyAsync {
        Thread.sleep(1000)
        num * 2
    }.await()
}

interface AsyncCaller {
    suspend fun call(): Int
}

class AsyncCallerImpl : AsyncCaller {
    override suspend fun call(): Int {
        return 100
    }
}