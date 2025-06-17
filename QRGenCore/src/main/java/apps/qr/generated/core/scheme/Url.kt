package apps.qr.generated.core.scheme

import java.net.MalformedURLException
import java.net.URL
import java.util.Locale

class Url : Schema<Url> {

    private var url: URL? = null

    fun getUrl(): String? {
        return url?.toString()
    }

    fun setUrl(urlStr: String) {
        url = try {
            URL(urlStr)
        } catch (e: MalformedURLException) {
            null // Ignore invalid URL
        }
    }

    override fun parseSchema(code: String?): Url {
        if (code == null || !(code.trim().lowercase(Locale.getDefault())
                .startsWith("http://") || code.trim().lowercase(Locale.getDefault())
                .startsWith("https://"))
        ) {
            throw IllegalArgumentException("this is not a valid url code: $code")
        }
        setUrl(code.trim())
        return this
    }

    override fun generateString(): String = getUrl() ?: ""

    override fun toString(): String = generateString()

    companion object {
        @JvmStatic
        fun parse(code: String): Url {
            val url = Url()
            url.parseSchema(code)
            return url
        }
    }
}
