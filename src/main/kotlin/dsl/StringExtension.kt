package dsl


fun StringBuilder.appendNew(string: String, ident: String = "  ", times: Int = 0) {
    (1..times).forEach { _ ->
        append(ident)
    }

    append(string)
    append("\n")
}

fun String.addIdent(ident: String, times: Int = 0): String {
    val identAll = (1..times).joinToString("") { ident }
    return this.split("\n")
        .joinToString("\n") { "$identAll$it" }
}


operator fun String.minus(value: String): Environment {
    return Environment(
        key = this,
        value = value
    )
}