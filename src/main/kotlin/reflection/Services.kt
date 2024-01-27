package reflection


@MyClass
class AService {
    fun print() {
        println("AService 입니다.")
    }
}

@MyClass
class BService(
    private val aService: AService,
    private val cService: CService?,
) {

    constructor(aService: AService) : this(aService, null) {
        println("BService 생성자 호출")
    }

    fun print() {
        print("BService 가 포함한 의존성: ")
        aService.print()
    }
}

class CService()

annotation class MyClass