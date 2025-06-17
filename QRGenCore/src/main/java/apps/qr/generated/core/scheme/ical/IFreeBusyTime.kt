package apps.qr.generated.core.scheme.ical

import android.icu.lang.UCharacter.LineBreak.LINE_FEED


internal class IFreeBusyTime : ICalSubSchema<IFreeBusyTime?> {
    override fun parseSchema(parameters: Map<String, String>, code: String): IFreeBusyTime? {
        return null
    }

    override fun generateString(): String {
        return BEGIN_TODO + LINE_FEED + LINE_FEED + "END:VFREEBUSY"
    }

    override fun toString(): String {
        return generateString()
    }


    companion object {
        const val NAME: String = "VFREEBUSY"
        private const val BEGIN_TODO = "BEGIN:VFREEBUSY"
    }
}