package lazy

import kotlin.properties.Delegates.notNull

class Person {
    lateinit var name: String
    var age: Int by notNull()

    val isKim: Boolean
        get() = name.startsWith("김")

    val maskingName: String
        get() = name.replaceRange(1, name.length, "*".repeat(name.length - 1))

}