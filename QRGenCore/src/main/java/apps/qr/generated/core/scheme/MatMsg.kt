package apps.qr.generated.core.scheme

import apps.qr.generated.core.scheme.utils.SchemeUtil

data class MatMsg(
    var to: String? = null,
    var cc: String? = null,
    var bcc: String? = null,
    var body: String? = null,
    var subject: String? = null
) : Schema<MatMsg> {

    override fun parseSchema(code: String?): MatMsg {
        require(!code.isNullOrBlank() && code.startsWith(BEGIN_MATMSG)) {
            "this is not a valid MATMSG code: $code"
        }

        val parameters = SchemeUtil.getParameters(code)

        if (parameters.containsKey(TO)) to = parameters[TO]
        if (parameters.containsKey(CC)) cc = parameters[CC]
        if (parameters.containsKey(BCC)) bcc = parameters[BCC]
        if (parameters.containsKey(BODY)) body = parameters[BODY]
        if (parameters.containsKey(SUBJECT)) subject = parameters[SUBJECT]

        return this
    }

    override fun generateString(): String {
        return buildString {
            append(BEGIN_MATMSG).append(LINE_FEED)

            to?.let { append(LINE_FEED).append("$TO:$it") }
            cc?.let { append(LINE_FEED).append("$CC:$it") }
            bcc?.let { append(LINE_FEED).append("$BCC:$it") }
            subject?.let { append(LINE_FEED).append("$SUBJECT:$it") }
            body?.let { append(LINE_FEED).append("$BODY:$it") }

            append(LINE_FEED).append("END:MATMSG")
        }
    }

    override fun toString(): String = generateString()

    companion object {
        private const val BEGIN_MATMSG = "BEGIN:MATMSG"
        private const val TO = "TO"
        private const val CC = "CC"
        private const val BCC = "BCC"
        private const val BODY = "BODY"
        private const val SUBJECT = "SUB"
        private const val LINE_FEED = "\n"

        @JvmStatic
        fun parse(matMsgCode: String): MatMsg {
            return MatMsg().apply {
                parseSchema(matMsgCode)
            }
        }
    }
}
