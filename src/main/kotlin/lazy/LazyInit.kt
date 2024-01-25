package lazy

class Person {
    lateinit var name: String

    val isKim: Boolean
        get() = name.startsWith("김")

    val maskingName: String
        get() = name.replaceRange(1, name.length, "*".repeat(name.length - 1))

}