package apps.qr.generated.core.scheme

import apps.qr.generated.core.scheme.utils.SchemeUtil

class SMS : Schema<SMS> {
    var number: String? = null
        private set
    var subject: String? = null
        private set

    constructor()

    constructor(number: String?, subject: String?) {
        this.number = number
        this.subject = subject
    }

    fun setNumber(number: String?) = apply { this.number = number }

    fun setSubject(subject: String?) = apply { this.subject = subject }

    override fun parseSchema(code: String?): SMS {
        if (code == null || !code.trim().lowercase().startsWith(SMS)) {
            throw IllegalArgumentException("this is not a valid sms code: $code")
        }
        val parameters = SchemeUtil.getParameters(code.trim().lowercase())
        parameters[SMS]?.let { setNumber(it) }
        number?.let { num -> parameters[num]?.let { setSubject(it) } }
        return this
    }

    override fun generateString(): String {
        return "$SMS:$number${subject?.let { ":$it" } ?: ""}"
    }

    override fun toString(): String = generateString()

    companion object {
        private const val SMS = "SMSTO"

        fun parse(code: String): SMS {
            return SMS().apply { parseSchema(code) }
        }
    }
}