package apps.qr.generated.core.scheme

data class GooglePlay(
    private var appPackage: String? = null
) : Schema<GooglePlay> {
    override fun parseSchema(code: String?): GooglePlay {
        if (code.isNullOrBlank() || !code.trim().lowercase().startsWith("{{{market:")) {
            throw IllegalArgumentException("this is not a google play code: $code")
        }
        val paths = code.trim().lowercase().removeSuffix("}}}").split("=")
        if (paths.size > 1) {
            appPackage = paths[1]
        }
        return this
    }

    override fun generateString(): String {
        return GPLAY.format(appPackage ?: "")
    }

    override fun toString(): String = generateString()

    companion object {
        const val GPLAY = "{{{market://details?id=%s}}}"

        @JvmStatic
        fun parse(code: String?): GooglePlay {
            return GooglePlay().parseSchema(code)
        }
    }
}