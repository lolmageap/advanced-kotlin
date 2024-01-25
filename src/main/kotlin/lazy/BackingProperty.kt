package lazy

class Person2 {
    private var _name: String? = null
    val name: String
        get() {
            if (_name == null) {
                Thread.sleep(2_000)
                this._name = "김병기"
            }
            return this._name!!
        }
}