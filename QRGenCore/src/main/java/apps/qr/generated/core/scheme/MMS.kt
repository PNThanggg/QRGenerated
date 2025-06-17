package apps.qr.generated.core.scheme

import apps.qr.generated.core.scheme.utils.SchemeUtil

data class MMS(
    private var number: String? = null, private var subject: String? = null
) : Schema<MMS> {

    override fun parseSchema(code: String?): MMS {
        require(!code.isNullOrBlank() && code.trim().lowercase().startsWith(MMS)) {
            "this is not a valid sms code: $code"
        }

        val parameters = SchemeUtil.getParameters(code.trim().lowercase())
        if (parameters.containsKey(MMS)) {
            number = parameters[MMS]
        }
        number?.let {
            if (parameters.containsKey(it)) {
                subject = parameters[it]
            }
        }

        return this
    }

    override fun generateString(): String {
        return buildString {
            append(MMS)
            append(":")
            append(number)
            subject?.let {
                append(":")
                append(it)
            }
        }
    }

    override fun toString(): String = generateString()

    companion object {
        private const val MMS = "mms"

        @JvmStatic
        fun parse(mmsCode: String): MMS {
            return MMS().apply {
                parseSchema(mmsCode)
            }
        }
    }
}