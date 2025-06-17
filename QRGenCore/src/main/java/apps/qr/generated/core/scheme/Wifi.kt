package apps.qr.generated.core.scheme

import apps.qr.generated.core.scheme.utils.SchemeUtil

open class Wifi : Schema<Wifi> {
    private var authentication: String? = null
    var ssid: String? = null
    var psk: String? = null
    var hidden: Boolean = false
        private set

    fun setAuthentication(auth: Authentication) {
        this.authentication = auth.name
    }

    fun withAuthentication(auth: Authentication): Wifi {
        setAuthentication(auth)
        return this
    }

    fun withSsid(ssid: String): Wifi {
        this.ssid = ssid
        return this
    }

    fun withPsk(psk: String): Wifi {
        this.psk = psk
        return this
    }

    fun withHidden(hidden: Boolean): Wifi {
        this.hidden = hidden
        return this
    }

    fun setHidden(value: String) {
        hidden = value.toBoolean()
    }

    override fun parseSchema(code: String?): Wifi {
        if (code == null || !code.startsWith(WIFI_PROTOCOL_HEADER)) {
            throw IllegalArgumentException("Không phải mã WIFI hợp lệ: $code")
        }

        val parameters =
            SchemeUtil.getParameters(code.substring(WIFI_PROTOCOL_HEADER.length), "(?<!\\\\);")

        ssid = parameters[SSID]?.let { unescape(it) }
        authentication = parameters[AUTHENTICATION]
        psk = parameters[PSK]?.let { unescape(it) }
        parameters[HIDDEN]?.let { setHidden(it) }

        return this
    }

    override fun generateString(): String {
        val sb = StringBuilder(WIFI_PROTOCOL_HEADER)
        ssid?.let { sb.append("$SSID:${escape(it)};") }
        authentication?.let { sb.append("$AUTHENTICATION:$it;") }
        psk?.let { sb.append("$PSK:${escape(it)};") }
        sb.append("$HIDDEN:$hidden;")
        return sb.toString()
    }

    override fun toString(): String = generateString()

    companion object {
        const val WIFI_PROTOCOL_HEADER = "WIFI:"
        const val AUTHENTICATION = "T"
        const val SSID = "S"
        const val PSK = "P"
        const val HIDDEN = "H"

        @JvmStatic
        fun parse(wifiCode: String): Wifi {
            val wifi = Wifi()
            wifi.parseSchema(wifiCode)
            return wifi
        }

        @JvmStatic
        fun escape(text: String): String {
            return text.replace("\\", "\\\\").replace(",", "\\,").replace(";", "\\;")
                .replace(".", "\\.").replace("\"", "\\\"").replace("'", "\\'")
        }

        @JvmStatic
        fun unescape(text: String): String {
            return text.replace("\\\\", "\\").replace("\\,", ",").replace("\\;", ";")
                .replace("\\.", ".").replace("\\\"", "\"").replace("\\'", "'")
        }
    }

    enum class Authentication {
        WEP, WPA, nopass
    }
}