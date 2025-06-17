package apps.qr.generated.core.scheme.ical


interface ICalSubSchema<T> {
    fun parseSchema(parameters: Map<String, String>, code: String): T

    fun generateString(): String
}