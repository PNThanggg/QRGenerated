package apps.qr.generated.core.scheme

import apps.qr.generated.core.scheme.utils.SchemeUtil

data class MeCard(
    var name: String? = null,
    var address: String? = null,
    var telephone: String? = null,
    var email: String? = null
) : Schema<MeCard> {
    override fun parseSchema(code: String?): MeCard {
        if (code == null || !code.startsWith(BEGIN_MECARD)) {
            throw IllegalArgumentException("this is not a valid MeCard code: $code")
        }
        val parameters =
            SchemeUtil.getParameters(code.replaceFirst("$BEGIN_MECARD:", ""), LINE_SEPARATOR, ":")
        parameters[NAME]?.let { name = it }
        parameters[ADDRESS]?.let { address = it }
        parameters[TEL]?.let { telephone = it }
        parameters[EMAIL]?.let { email = it }
        return this
    }

    override fun generateString(): String {
        val sb = StringBuilder()
        sb.append("$BEGIN_MECARD:")
        name?.let { sb.append(NAME).append(":").append(it).append(LINE_SEPARATOR) }
        address?.let { sb.append(ADDRESS).append(":").append(it).append(LINE_SEPARATOR) }
        telephone?.let { sb.append(TEL).append(":").append(it).append(LINE_SEPARATOR) }
        email?.let { sb.append(EMAIL).append(":").append(it).append(LINE_SEPARATOR) }
        sb.append(LINE_SEPARATOR)
        return sb.toString()
    }

    /**
     * Returns the textual representation of this mecard of the form
     * MECARD:N:Doe,John;TEL:13035551212;EMAIL:john.doe@example.com;;
     */
    override fun toString(): String = generateString()

    companion object {
        private const val BEGIN_MECARD = "MECARD"
        private const val NAME = "N"
        private const val ADDRESS = "ADR"
        private const val TEL = "TEL"
        private const val EMAIL = "EMAIL"
        private const val LINE_SEPARATOR = ";"

        fun parse(meCardCode: String): MeCard {
            return MeCard().apply { parseSchema(meCardCode) }
        }
    }
}