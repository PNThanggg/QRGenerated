package apps.qr.generated.core.scheme

import apps.qr.generated.core.scheme.utils.SchemeUtil

data class KddiAu(
    private var name1: String? = null,
    private var name2: String? = null,
    private var address: String? = null,
    private var telephone1: String? = null,
    private var telephone2: String? = null,
    private var telephone3: String? = null,
    private var email1: String? = null,
    private var email2: String? = null,
    private var email3: String? = null,
) : Schema<KddiAu> {
    override fun parseSchema(code: String?): KddiAu {
        require(!code.isNullOrBlank() && code.startsWith(BEGIN)) {
            "this is not a valid KDDI AU code: $code"
        }

        val parameters = SchemeUtil.getParameters(code)

        parameters[NAME1]?.let { name1 = it }
        parameters[NAME2]?.let { name2 = it }
        parameters[ADD]?.let { address = it }
        parameters[TEL1]?.let { telephone1 = it }
        parameters[TEL2]?.let { telephone2 = it }
        parameters[TEL3]?.let { telephone3 = it }
        parameters[MAIL1]?.let { email1 = it }
        parameters[MAIL2]?.let { email2 = it }
        parameters[MAIL3]?.let { email3 = it }

        return this
    }

    override fun generateString(): String {
        return buildString {
            append(BEGIN).append(SchemeUtil.LINE_FEED)
            name1?.let { append("$NAME1:$it") }
            name2?.let { append("$NAME2:$it") }
            address?.let { append("$ADD:$it") }
            telephone1?.let { append("$TEL1:$it") }
            telephone2?.let { append("$TEL2:$it") }
            telephone3?.let { append("$TEL3:$it") }
            email1?.let { append("$MAIL1:$it") }
            email2?.let { append("$MAIL2:$it") }
            email3?.let { append("$MAIL3:$it") }
            append(SchemeUtil.LINE_FEED)
        }
    }

    override fun toString(): String = generateString()

    companion object {
        private const val BEGIN = "MEMORY"
        private const val NAME1 = "NAME1"
        private const val NAME2 = "NAME2"
        private const val MAIL1 = "MAIL1"
        private const val MAIL2 = "MAIL2"
        private const val MAIL3 = "MAIL3"
        private const val TEL1 = "TEL1"
        private const val TEL2 = "TEL2"
        private const val TEL3 = "TEL3"
        private const val ADD = "ADD"

        @JvmStatic
        fun parse(code: String?): KddiAu = KddiAu().parseSchema(code)
    }
}