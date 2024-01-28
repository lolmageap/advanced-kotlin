package tip

fun main() {

}

fun recursiveFactorial(n: Int) : Int {
    return if (n <= 1) {
        n
    } else {
        n * recursiveFactorial(n - 1)
    }
}

fun recursiveFactorialV2(n: Int, curr: Int = 1) : Int {
    return if (n <= 1) {
        curr
    } else {
        recursiveFactorialV2(n - 1, curr * n)
    }
}

@Suppress("NO_TAIL_CALLS_FOUND")
tailrec fun recursiveFactorialV3(n: Int, curr: Int = 1) : Int {
    return if (n <= 1) {
        curr
    } else {
        recursiveFactorialV2(n - 1, curr * n)
    }
}
