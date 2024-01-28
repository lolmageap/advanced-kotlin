
package tip

import java.lang.RuntimeException
import kotlin.reflect.KClass

@Suppress("UNREACHABLE_CODE")
fun main() {
    val key = Key("key")
    println(key.key)

    //

    try {
        TODO("아무 것도 실행 안함")
    } catch (e: Exception) {
        when (e) {
            is AException -> println("A")
            is BException -> println("B")
            is CException -> println("C")
        }
        throw e
    }

    //

    runCatching { TODO("아무 것도 실행 안함") }
        .onError(AException::class, BException::class) { e ->
            println("A, B 에러 처리")
        }
        .onError(CException::class) { e ->
            println("C 에러 처리")
        }
}

/**
 *  Key Class 는 생성 시점에 String 으로 변환 되어 출력 된다.
 ** 대신 property 를 하나만 가지고 있어야 한다.
 */
@JvmInline
value class Key(val key: String)

data class UserV1(val name: String? = null, val id: Long)

data class BookV1(val name: String? = null, val id: Long)

fun handleV1(userId: Long, bookId: Long) {

}

data class UserV2(val name: String? = null, val id: Long)

data class BookV2(val name: String? = null, val id: Long)

fun handleV2(userId: Id<UserV2>, bookId: Id<BookV2>) {

}

@JvmInline
value class Id<T>(
    val id: Long = 0
)

class AException : RuntimeException()
class BException : RuntimeException()
class CException : RuntimeException()

class ResultWrapper<T>(
    private val result: Result<T>,
    private val unknownExceptions: MutableList<KClass<out Throwable>>,
) {

    fun toResult(): Result<T> {
        return result
    }

    fun onError(
        vararg exceptions: KClass<out Throwable>,
        action: (Throwable) -> Unit,
    ): ResultWrapper<T> {
        result.exceptionOrNull()?.let { exception ->
            if(exception::class in exceptions && exception::class !in unknownExceptions) {
                action(exception)
            }
        }
        return this
    }

}

fun <T> Result<T>.onError(
    vararg exceptions: KClass<out Throwable>,
    action: (Throwable) -> Unit,
): ResultWrapper<T> {
    exceptionOrNull()?.let { exception ->
        if(exception::class in exceptions) {
            action(exception)
        }
    }
    return ResultWrapper(this, exceptions.toMutableList())
}