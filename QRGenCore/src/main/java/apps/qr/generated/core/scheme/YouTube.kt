package apps.qr.generated.core.scheme

import apps.qr.generated.core.scheme.utils.SchemeUtil

data class YouTube(
    private var videoId: String? = null
) : Schema<YouTube> {

    companion object {
        const val YOUTUBE = "youtube"

        @JvmStatic
        fun parse(code: String): YouTube {
            val youTube = YouTube()
            youTube.parseSchema(code)
            return youTube
        }
    }

    override fun parseSchema(code: String?): YouTube {
        if (code == null || !code.lowercase().startsWith(YOUTUBE)) {
            throw IllegalArgumentException("Không phải mã YouTube hợp lệ: $code")
        }
        val parameters: Map<String, String> = SchemeUtil.getParameters(code)
        if (parameters.containsKey(YOUTUBE)) {
            videoId = parameters[YOUTUBE]
        }
        return this
    }

    override fun generateString(): String {
        return "$YOUTUBE:$videoId"
    }

    override fun toString(): String {
        return generateString()
    }
}