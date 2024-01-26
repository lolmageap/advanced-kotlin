package function

fun main() {

    // 1. 람다식 : 반환 타입을 적을 수 없다
    compute(1, 2) { a, b -> a + b }

    // 2. 익명 함수 : 반환 타입을 적을 수 있다
    compute(1, 2, fun(a, b) = a + b)

    val calculate = calculate(5, 2, Operator.TIMES)
    println(calculate)
}

fun compute(
    num1: Int,
    num2: Int,
    operation: (Int, Int) -> Int = { a, b -> a + b },
): Int {
    return operation(num1, num2)
}

fun calculate(
    num1: Int,
    num2: Int,
    operator: Operator,
) = operator(num1, num2)


enum class Operator(
    private val op: Char,
    private val opFunc: (Int, Int) -> Int,
) {
    PLUS('+', { a, b -> a + b }),
    MINUS('-', { a, b -> a - b }),
    TIMES('*', { a, b -> a * b }),
    DIVIDE('/', { a, b ->
        if (b == 0) throw IllegalArgumentException("0으로 나눌 수 없습니다.")
        else a / b
    });

    fun operate(a: Int, b: Int): Int {
        return opFunc(a, b)
    }

    operator fun invoke(a: Int, b: Int): Int {
        return opFunc(a, b)
    }
}