package apps.qr.generated.core.scheme


interface Schema<T> {
    /**
     * Parses a given string for a QR code schema
     *
     * @param code to be parsed
     * @return schema
     */
    fun parseSchema(code: String?): T

    /**
     * Generates code string.
     *
     * @return code
     */
    fun generateString(): String
}