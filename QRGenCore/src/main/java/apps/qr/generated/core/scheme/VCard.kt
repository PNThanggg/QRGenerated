package apps.qr.generated.core.scheme

import apps.qr.generated.core.scheme.utils.SchemeUtil

data class VCard(
    private var name: String? = null,
    private var company: String? = null,
    private var title: String? = null,
    private var phoneNumber: String? = null,
    private var email: String? = null,
    private var address: String? = null,
    private var website: String? = null,
    private var note: String? = null,
) : Schema<VCard> {
    companion object {
        private const val BEGIN_VCARD = "BEGIN:VCARD"
        private const val NAME = "N"
        private const val COMPANY = "ORG"
        private const val TITLE = "TITLE"
        private const val PHONE = "TEL"
        private const val WEB = "URL"
        private const val EMAIL = "EMAIL"
        private const val ADDRESS = "ADR"
        private const val NOTE = "NOTE"
        private const val LINE_FEED = "\n"

        @JvmStatic
        fun parse(code: String): VCard {
            val vCard = VCard()
            vCard.parseSchema(code)
            return vCard
        }
    }

    override fun parseSchema(code: String?): VCard {
        if (code == null || !code.startsWith(BEGIN_VCARD)) {
            throw IllegalArgumentException("this is not a valid VCARD code: $code")
        }

        val parameters = SchemeUtil.getParameters(code)
        name = parameters[NAME]
        title = parameters[TITLE]
        company = parameters[COMPANY]
        address = parameters[ADDRESS]
        email = parameters[EMAIL]
        website = parameters[WEB]
        phoneNumber = parameters[PHONE]
        note = parameters[NOTE]

        return this
    }

    override fun generateString(): String {
        val sb = StringBuilder()
        sb.append(BEGIN_VCARD).append(LINE_FEED)
        sb.append("VERSION:3.0").append(LINE_FEED)
        name?.let { sb.append(NAME).append(":").append(it) }
        company?.let { sb.append(LINE_FEED).append(COMPANY).append(":").append(it) }
        title?.let { sb.append(LINE_FEED).append(TITLE).append(":").append(it) }
        phoneNumber?.let { sb.append(LINE_FEED).append(PHONE).append(":").append(it) }
        website?.let { sb.append(LINE_FEED).append(WEB).append(":").append(it) }
        email?.let { sb.append(LINE_FEED).append(EMAIL).append(":").append(it) }
        address?.let { sb.append(LINE_FEED).append(ADDRESS).append(":").append(it) }
        note?.let { sb.append(LINE_FEED).append(NOTE).append(":").append(it) }
        sb.append(LINE_FEED).append("END:VCARD")
        return sb.toString()
    }

    override fun toString(): String = generateString()
}