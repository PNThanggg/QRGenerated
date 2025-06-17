package apps.qr.generated.core.scheme.utils

object SchemeUtil {
    const val LINE_FEED = "\n"
    private const val DEFAULT_PARAM_SEPARATOR = "\r?\n"
    private const val DEFAULT_KEY_VALUE_SEPARATOR = ":"

    @JvmStatic
    fun getParameters(qrCode: String, paramSeparator: String): Map<String, String> {
        return getParameters(qrCode, paramSeparator, DEFAULT_KEY_VALUE_SEPARATOR)
    }

    @JvmStatic
    fun getParameters(qrCode: String): Map<String, String> {
        return getParameters(qrCode, DEFAULT_PARAM_SEPARATOR, DEFAULT_KEY_VALUE_SEPARATOR)
    }

    @JvmStatic
    fun getParameters(
        qrCode: String, paramSeparator: String, keyValueSeparator: String
    ): Map<String, String> {
        val result = LinkedHashMap<String, String>()
        val parts = qrCode.split(Regex(paramSeparator))
        for (part in parts) {
            val param = part.split(keyValueSeparator, limit = 2)
            if (param.size > 1) {
                result[param[0]] = param[1]
            }
        }
        return result
    }
}