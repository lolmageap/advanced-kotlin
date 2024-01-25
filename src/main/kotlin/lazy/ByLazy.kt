package lazy

import kotlin.properties.Delegates.observable
import kotlin.properties.Delegates.vetoable
import kotlin.reflect.KProperty

fun main() {
    val person = Person5()
    person.age = 30
    person.weight = 40
    require(person.weight == 40)
}

class Person3 {
    val name: String by lazy {
        Thread.sleep(2_000)
        "김병기"
    }

    @Deprecated("사용하지 않음", ReplaceWith("age"))
    val num: Int = 0
    val age: Int by this::num
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

class LazyInitProperty<T>(val init: () -> T) {
    private var _value: T? = null
    val value: T
        get() {
            if (_value == null) {
                _value = init()
            }
            return _value!!
        }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        _value = value
    }
}

class DelegateProperty<T>