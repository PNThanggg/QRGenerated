package apps.qr.generated.core.scheme

import apps.qr.generated.core.scheme.utils.SchemeUtil

class EMail(
    private var email: String? = null,
) : Schema<EMail> {
    fun getEmail(): String? = email

    fun setEmail(email: String?): EMail {
        this.email = email
        return this
    }

    fun withEmail(email: String?): EMail = setEmail(email)

    override fun parseSchema(code: String?): EMail {
        if (code.isNullOrBlank() || !code.lowercase().startsWith(MAILTO)) {
            throw IllegalArgumentException("this is not a valid email code: $code")
        }

        val parameters = SchemeUtil.getParameters(code.lowercase())
        if (parameters.containsKey(MAILTO)) {
            setEmail(parameters[MAILTO])
        }
        return this
    }

    override fun generateString(): String {
        return "$MAILTO:$email"
    }

    override fun toString(): String = generateString()

    companion object {
        private const val MAILTO = "mailto"

        @JvmStatic
        fun parse(emailCode: String?): EMail {
            return EMail().parseSchema(emailCode)
        }
    }
}