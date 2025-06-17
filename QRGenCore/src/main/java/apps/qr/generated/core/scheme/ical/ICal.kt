package apps.qr.generated.core.scheme.ical

import apps.qr.generated.core.scheme.Schema
import apps.qr.generated.core.scheme.utils.SchemeUtil

data class ICal(
    private var subSchema: ICalSubSchema<*>? = null
) : Schema<ICal> {
    fun getSubSchema(): ICalSubSchema<*>? = subSchema

    fun getIEvent(): IEvent? = subSchema as? IEvent

    override fun parseSchema(code: String?): ICal {
        require(!code.isNullOrBlank() && code.startsWith(BEGIN_VCALENDAR)) {
            "this is not a valid ICal code: $code"
        }

        val parameters = SchemeUtil.getParameters(code)
        if (parameters.containsKey(IEvent.NAME)) {
            subSchema = IEvent().parseSchema(parameters, code)
        }

        return this
    }

    override fun generateString(): String {
        return buildString {
            append(BEGIN_VCALENDAR).append(SchemeUtil.LINE_FEED)
            append("VERSION:2.0").append(SchemeUtil.LINE_FEED)
            append("PRODID:-//hacksw/handcal//NONSGML v1.0//EN").append(SchemeUtil.LINE_FEED)
            subSchema?.let {
                append(it.generateString())
            }
            append(SchemeUtil.LINE_FEED).append("END:VCALENDAR")
        }
    }

    override fun toString(): String = generateString()

    companion object {
        private const val BEGIN_VCALENDAR = "BEGIN:VCALENDAR"

        fun of(subSchema: IEvent): ICal = ICal(subSchema)
    }
}