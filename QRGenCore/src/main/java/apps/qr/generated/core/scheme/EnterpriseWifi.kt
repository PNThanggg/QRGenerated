package apps.qr.generated.core.scheme

import apps.qr.generated.core.scheme.utils.SchemeUtil

class EnterpriseWifi : Wifi() {
    var user: String? = null
        private set
    var eap: String? = null
        private set
    var phase: String? = null
        private set

    fun withUser(user: String?) = apply { this.user = user }
    fun withEap(eap: String?) = apply { this.eap = eap }
    fun withPhase(phase: String?) = apply { this.phase = phase }

    fun setUser(user: String?) {
        withUser(user)
    }

    fun setEap(eap: String?) {
        withEap(eap)
    }

    fun setPhase(phase: String?) {
        withPhase(phase)
    }

    override fun parseSchema(code: String?): EnterpriseWifi {
        if (code == null || !code.startsWith(WIFI_PROTOCOL_HEADER)) {
            throw IllegalArgumentException("this is not a valid WIFI code: $code")
        }
        val parameters =
            SchemeUtil.getParameters(code.substring(WIFI_PROTOCOL_HEADER.length), "(?<!\\\\);")
        parameters[SSID]?.let { ssid = unescape(it) }
        parameters[PSK]?.let { psk = unescape(it) }
        parameters[USER]?.let { setUser(unescape(it)) }
        parameters[EAP]?.let { setEap(unescape(it)) }
        parameters[PHASE]?.let { setPhase(unescape(it)) }
        parameters[HIDDEN]?.let { setHidden(it) }
        return this
    }

    override fun generateString(): String {
        val bob = StringBuilder(WIFI_PROTOCOL_HEADER)
        ssid?.let { bob.append(SSID).append(":").append(escape(it)).append(";") }
        user?.let { bob.append(USER).append(":").append(escape(it)).append(";") }
        psk?.let { bob.append(PSK).append(":").append(escape(it)).append(";") }
        eap?.let { bob.append(EAP).append(":").append(escape(it)).append(";") }
        phase?.let { bob.append(PHASE).append(":").append(escape(it)).append(";") }
        bob.append(HIDDEN).append(":").append(hidden).append(";")
        return bob.toString()
    }

    override fun toString(): String = generateString()

    companion object {
        const val USER = "U"
        const val EAP = "E"
        const val PHASE = "PH"

        fun parse(wifiCode: String): EnterpriseWifi {
            return EnterpriseWifi().apply { parseSchema(wifiCode) }
        }

        fun escape(text: String): String {
            return text.replace("\\", "\\\\").replace(",", "\\,").replace(";", "\\;")
                .replace(".", "\\.").replace("\"", "\\\"").replace("'", "\\'")
        }

        fun unescape(text: String): String {
            return text.replace("\\\\", "\\").replace("\\,", ",").replace("\\;", ";")
                .replace("\\.", ".").replace("\\\"", "\"").replace("\\'", "'")
        }
    }
}