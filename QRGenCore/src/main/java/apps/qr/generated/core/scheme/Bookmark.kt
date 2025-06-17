package apps.qr.generated.core.scheme

import apps.qr.generated.core.scheme.utils.SchemeUtil

data class Bookmark(
    private var url: String? = null,
    private var title: String? = null,
) : Schema<Bookmark?> {
    fun withUrl(url: String?): Bookmark {
        this.url = url
        return this
    }

    fun withTitle(title: String?): Bookmark {
        this.title = title
        return this
    }

    override fun parseSchema(code: String?): Bookmark {
        require(!(code == null || !code.startsWith(BEGIN_BOOKMARK))) { "this is not a valid Bookmark code: $code" }
        val parameters: Map<String, String> = SchemeUtil.getParameters(
            code.replaceFirst(("$BEGIN_BOOKMARK:").toRegex(), ""), LINE_SEPARATOR, ":"
        )
        if (parameters.containsKey(URL)) {
            url = parameters[URL]
        }
        if (parameters.containsKey(TITLE)) {
            title = parameters[TITLE]
        }
        return this
    }

    override fun generateString(): String {
        val sb = StringBuilder()
        sb.append(BEGIN_BOOKMARK).append(":")
        if (url != null) {
            sb.append(URL).append(":").append(url).append(LINE_SEPARATOR)
        }
        if (title != null) {
            sb.append(TITLE).append(":").append(title).append(LINE_SEPARATOR)
        }
        sb.append(LINE_SEPARATOR)
        return sb.toString()
    }

    /**
     * Returns the textual representation of this bookmark of the form
     *
     *
     * MEBKM:URL:google.com;TITLE:Google;
     *
     */
    override fun toString(): String {
        return generateString()
    }

    companion object {
        private const val BEGIN_BOOKMARK = "MEBKM"
        private const val URL = "URL"
        private const val TITLE = "TITLE"
        private const val LINE_SEPARATOR = ";"
        fun parse(code: String): Bookmark {
            return Bookmark().parseSchema(code)
        }
    }
}