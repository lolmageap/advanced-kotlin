package lazy

import kotlin.reflect.KProperty

class Person3 {
    val name: String by lazy {
        Thread.sleep(2_000)
        "김병기"
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