package apps.qr.generated.core.scheme

import apps.qr.generated.core.scheme.utils.SchemeUtil

class Telephone(
    private var telephone: String? = null
) : Schema<Telephone> {
    fun getTelephone(): String? = telephone

    fun setTelephone(telephone: String?): Telephone {
        this.telephone = telephone
        return this
    }

    override fun parseSchema(code: String?): Telephone {
        if (code.isNullOrBlank() || !code.trim().lowercase().startsWith(TEL)) {
            throw IllegalArgumentException("this is not a valid telephone code: $code")
        }
        val parameters = SchemeUtil.getParameters(code.trim().lowercase())
        if (parameters.containsKey(TEL)) {
            setTelephone(parameters[TEL])
        }
        return this
    }

    override fun generateString(): String {
        return "$TEL:$telephone"
    }

    override fun toString(): String = generateString()

    companion object {
        private const val TEL = "tel"

        @JvmStatic
        fun parse(code: String?): Telephone {
            return Telephone().parseSchema(code)
        }
    }
}
