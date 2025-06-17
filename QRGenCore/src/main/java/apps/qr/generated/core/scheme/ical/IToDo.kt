package apps.qr.generated.core.scheme.ical

import android.icu.lang.UCharacter.LineBreak.LINE_FEED


internal class IToDo : ICalSubSchema<IToDo?> {
    override fun parseSchema(parameters: Map<String, String>, code: String): IToDo? {
        return null
    }

    override fun generateString(): String {
        return BEGIN_TODO + LINE_FEED + LINE_FEED + "END:VTODO"
    }

    override fun toString(): String {
        return generateString()
    }

    companion object {
        const val NAME: String = "VTODO"
        private const val BEGIN_TODO = "BEGIN:VTODO"
    }
}