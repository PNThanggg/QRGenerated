package apps.qr.generated.core.scheme.ical

import android.icu.lang.UCharacter.LineBreak.LINE_FEED


internal class IJournal : ICalSubSchema<IJournal?> {
    override fun parseSchema(parameters: Map<String, String>, code: String): IJournal? {
        return null
    }

    override fun generateString(): String {
        val sb = StringBuilder()
        sb.append(BEGIN_TODO).append(LINE_FEED)
        sb.append(LINE_FEED).append("END:VJOURNAL")
        return sb.toString()
    }

    override fun toString(): String {
        return generateString()
    }

    companion object {
        const val NAME: String = "VJOURNAL"
        private const val BEGIN_TODO = "BEGIN:VJOURNAL"
    }
}