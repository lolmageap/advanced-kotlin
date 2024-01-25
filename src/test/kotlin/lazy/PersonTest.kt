package lazy

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PersonTest : StringSpec({

    "isKim" {
        val person = Person()
        person.apply { name = "김병기" }
        person.isKim shouldBe true
    }

    "maskingName" {
        val person = Person()
        person.apply { name = "김병기" }

        person.maskingName shouldBe "김**"
    }

})
