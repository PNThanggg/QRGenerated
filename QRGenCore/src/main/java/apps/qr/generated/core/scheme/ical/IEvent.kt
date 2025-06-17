package apps.qr.generated.core.scheme.ical

import apps.qr.generated.core.scheme.utils.SchemeUtil

class IEvent : ICalSubSchema<IEvent> {

    companion object {
        const val NAME = "VEVENT"
        private const val BEGIN_EVENT = "BEGIN:VEVENT"
        private const val UID = "UID"
        private const val STAMP = "DTSTAMP"
        private const val ORGANIZER = "ORGANIZER"
        private const val START = "DTSTART"
        private const val END = "DTEND"
        private const val SUMMARY = "SUMMARY"

        fun parse(parameters: Map<String, String>, code: String): IEvent {
            return IEvent().parseSchema(parameters, code)
        }
    }

    var uid: String? = null
    var stamp: String? = null
    var organizer: String? = null
    var start: String? = null
    var end: String? = null
    var summary: String? = null

    override fun parseSchema(parameters: Map<String, String>, code: String): IEvent {
        uid = parameters[UID]
        stamp = parameters[STAMP]
        start = parameters[START]
        end = parameters[END]
        summary = parameters[SUMMARY]

        val param = SchemeUtil.getParameters(code)
        return this
    }

    override fun generateString(): String {
        return buildString {
            append(BEGIN_EVENT).append(SchemeUtil.LINE_FEED)
            uid?.let { append("$UID:$it${SchemeUtil.LINE_FEED}") }
            stamp?.let { append("$STAMP:$it${SchemeUtil.LINE_FEED}") }
            organizer?.let { append("$ORGANIZER;$it${SchemeUtil.LINE_FEED}") }
            start?.let { append("$START:$it${SchemeUtil.LINE_FEED}") }
            end?.let { append("$END:$it${SchemeUtil.LINE_FEED}") }
            summary?.let { append("$SUMMARY:$it${SchemeUtil.LINE_FEED}") }
            append(SchemeUtil.LINE_FEED).append("END:VEVENT")
        }
    }

    override fun toString(): String = generateString()
}