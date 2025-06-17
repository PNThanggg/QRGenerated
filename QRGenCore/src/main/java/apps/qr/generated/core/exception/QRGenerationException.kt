package apps.qr.generated.core.exception

class QRGenerationException(
    message: String,
    underlyingException: Throwable? = null,
) : RuntimeException(
    message, underlyingException
)