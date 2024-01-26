package example

import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
open class Sequence {

    private val fruits = mutableListOf<Fruit>()
    @Setup
    fun setup() {
        repeat(2_000_000) {
            fruits.add(Fruit.randomFruit())
        }
    }

    fun kotlinSequence() {
        fruits.asSequence()
            .filter { it.name == "사과" }
            .map { it.price }
            .take(1_000)
            .average()
    }

    fun kotlinIterable() {
        fruits.filter { it.name == "사과" }
            .map { it.price }
            .take(1_000)
            .average()
    }

}

data class Fruit(
    val name: String,
    val price: Int,
) {
    companion object {
        private val NAME_CANDIDATES = listOf("사과", "배", "포도", "딸기", "키위")
        fun randomFruit(): Fruit {
            val name = NAME_CANDIDATES.random()
            val price = (1000..20000).random()
            return Fruit(name, price)
        }
    }
}
