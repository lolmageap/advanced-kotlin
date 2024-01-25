package lazy

import kotlin.properties.Delegates.observable
import kotlin.properties.Delegates.vetoable
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun main() {
    val person = Person5()
    person.age = 30
    person.weight = 40
    require(person.weight == 50)
}

class Person3 {
    val name: String by lazy {
        Thread.sleep(2_000)
        "김병기"
    }

    @Deprecated("사용하지 않음", ReplaceWith("age"))
    val num: Int = 0
    val age: Int by this::num

    val status: String by object: ReadOnlyProperty<Person3, String> {
        private var isGreen: Boolean = false

        override fun getValue(thisRef: Person3, property: KProperty<*>): String {
            return if (isGreen) "Green" else "Red"
        }
    }
}

// Person3 클래스 와 Person4 클래스 는 동일한 기능을 수행 한다.

class Person4 {
    private val delegateProperty = LazyInitProperty {
        Thread.sleep(2_000)
        "김병기"
    }

    val name: String
        get() = delegateProperty.value
}

class Person5 {
    var age: Int by observable(20) { _, oldValue, newValue ->
        if (oldValue != newValue) {
            println("이전 값: $oldValue")
            println("새로운 값: $newValue")
        }
    }

    // weight 값이 old value 보다 크면 true 를 반환 하고, 그렇지 않으면 false 를 반환 한다.
    var weight: Int by vetoable(50) { _, oldValue, newValue ->
        newValue > oldValue
    }
}

class Person6 {
    val name by DelegateProperty("김병기")
    val country by DelegateProperty("대한민국")
}

class LazyInitProperty<T>(val init: () -> T): ReadOnlyProperty<Any, T> {
    private var _value: T? = null
    val value: T
        get() {
            if (_value == null) {
                _value = init()
            }
            return _value!!
        }

//    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
//        return value
//    }
//
//    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
//        _value = value
//    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return value
    }
}

class DelegateProvider(
    private val initValue: String,
): PropertyDelegateProvider<Any, DelegateProperty> {
    override fun provideDelegate(thisRef: Any, property: KProperty<*>): DelegateProperty {
        if (property.name != "name") {
            throw IllegalArgumentException("name 이 아닌 프로퍼티는 사용할 수 없습니다.")
        }
        return DelegateProperty(initValue)
    }
}

class DelegateProperty(
    private val initValue: String
): ReadOnlyProperty<Any, String> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return initValue
    }
}

interface Fruit {
    val name: String
    val color: String
    fun bite()
}

open class Apple: Fruit {
    override val name: String
        get() = "사과"
    override val color: String
        get() = "빨간색"

    override fun bite() {
        println("사과를 먹습니다.")
    }
}

class BlueApple: Apple() {
    override val color: String
        get() = "파란색"
}

class GreenApple(
    private val apple: Apple
): Fruit by apple {
    override val color: String
        get() = "초록색"
}