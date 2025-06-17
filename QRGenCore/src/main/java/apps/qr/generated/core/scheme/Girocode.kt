package apps.qr.generated.core.scheme

data class Girocode(
    var name: String? = null,
    var iban: String? = null,
    var bic: String? = null,
    var amount: String? = null,
    var purposeCode: String? = null,
    var reference: String? = null,
    var text: String? = null,
    var encoding: Encoding? = null,
    var hint: String? = null
) : Schema<Girocode> {

    companion object {
        const val SERVICE_HEADER = "BCD"
        const val FUNCTION_SEPA_CREDIT_TRANSFER = "SCT"
        const val VERSION_1 = "001"
        const val LINE_FEED = "\n"
        const val DEFAULT_PARAM_SEPARATOR = "\n"

        fun parse(qrCode: String?): Girocode {
            val girocode = Girocode()
            girocode.parseSchema(qrCode)
            return girocode
        }
    }

    enum class Encoding {
        UTF_8, ISO_8859_1, ISO_8859_2, ISO_8859_4, ISO_8859_5, ISO_8859_7, ISO_8859_10, ISO_8859_15;

        fun value(): String {
            return (ordinal + 1).toString()
        }

        override fun toString(): String {
            return value()
        }

        companion object {
            fun encodingFor(value: String): Encoding {
                return entries.find { it.value() == value }
                    ?: throw IllegalArgumentException("unknown encoding value '$value'")
            }
        }
    }

    override fun parseSchema(code: String?): Girocode {
        if (code == null) {
            throw IllegalArgumentException("null is not a valid Girocode")
        }
        val params = code.split(DEFAULT_PARAM_SEPARATOR)
        if (params.size < 6 || params[0] == "SERVICE_HEADER") {
            throw IllegalArgumentException("this is not a valid Girocode: $code")
        }
        encoding = Encoding.encodingFor(params[2])
        bic = params[4]
        name = params[5]
        iban = params[6]
        if (params.size > 7) amount = params[7]
        if (params.size > 8) purposeCode = params[8]
        if (params.size > 9) reference = params[9]
        if (params.size > 10) text = params[10]
        if (params.size > 11) hint = params[11]
        return this
    }

    override fun generateString(): String {
        return buildString {
            append(SERVICE_HEADER).append(LINE_FEED)
            append(VERSION_1).append(LINE_FEED)
            append(encoding?.toString() ?: "").append(LINE_FEED)
            append(FUNCTION_SEPA_CREDIT_TRANSFER).append(LINE_FEED)
            append(bic ?: "").append(LINE_FEED)
            append(name ?: "").append(LINE_FEED)
            append(iban ?: "").append(LINE_FEED)
            append(amount ?: "").append(LINE_FEED)
            append(purposeCode ?: "").append(LINE_FEED)
            append(reference ?: "").append(LINE_FEED)
            append(text ?: "").append(LINE_FEED)
            append(hint ?: "").append(LINE_FEED)
        }
    }

    override fun toString(): String {
        return generateString()
    }
}