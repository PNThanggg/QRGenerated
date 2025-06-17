package apps.qr.generated.core

import android.graphics.Bitmap
import apps.qr.generated.core.exception.QRGenerationException
import apps.qr.generated.core.image.ImageType
import apps.qr.generated.core.scheme.Schema
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.io.File
import java.io.IOException
import java.io.OutputStream

class QRCode private constructor(
    private val text: String
) : AbstractQRCode() {
    private var matrixToImageConfig: MatrixToImageConfig.Config = MatrixToImageConfig.Config()

    init {
        qrWriter = QRCodeWriter()
    }

    companion object {
        /**
         * Create a QR code from the given text.
         * There is a size limitation to how much you can put into a QR code. This
         * has been tested to work with up to a length of 2950 characters.
         * The QRCode will have the following defaults:
         * {size: 100x100}
         * {imageType: PNG}
         * Both size and imageType can be overridden:
         * Image type override is done by calling [to].
         * Size override is done by calling [withSize].
         *
         * @param text The text to encode to a new QRCode, this may fail if the text is too large.
         * @return The QRCode object
         */
        fun from(text: String): QRCode {
            return QRCode(text)
        }

        /**
         * Creates a QR Code from the given [Schema].
         * The QRCode will have the following defaults:
         * {size: 100x100}
         * {imageType: PNG}
         *
         * @param schema The schema to encode as QRCode
         * @return The QRCode object
         */
        fun from(schema: Schema<*>): QRCode {
            return QRCode(schema.generateString())
        }
    }

    /**
     * Overrides the imageType from its default [ImageType.PNG].
     *
     * @param imageType The [ImageType] you would like the resulting QR to be
     * @return The current QRCode object
     */
    fun to(imageType: ImageType): QRCode {
        this.imageType = imageType
        return this
    }

    /**
     * Overrides default colors (black on white).
     * Notice that the color format is "0x(alpha: 1 byte)(RGB: 3 bytes)".
     *
     * @param onColor The color for the QR code
     * @param offColor The color for the background
     * @return The current QRCode object
     */
    fun withColor(onColor: Int, offColor: Int): QRCode {
        matrixToImageConfig = MatrixToImageConfig.Config(onColor, offColor)
        return this
    }

    /**
     * Overrides the size of the QR from its default 125x125.
     *
     * @param width The width in pixels
     * @param height The height in pixels
     * @return The current QRCode object
     */
    fun withSize(width: Int, height: Int): QRCode {
        this.width = width
        this.height = height
        return this
    }

    /**
     * Overrides the default charset by supplying a [EncodeHintType.CHARACTER_SET] hint to [QRCodeWriter.encode].
     *
     * @return The current QRCode object
     */
    fun withCharset(charset: String): QRCode {
        return withHint(EncodeHintType.CHARACTER_SET, charset)
    }

    /**
     * Overrides the default error correction by supplying a [EncodeHintType.ERROR_CORRECTION] hint to [QRCodeWriter.encode].
     *
     * @return The current QRCode object
     */
    fun withErrorCorrection(level: ErrorCorrectionLevel): QRCode {
        return withHint(EncodeHintType.ERROR_CORRECTION, level)
    }

    /**
     * Sets hint to [QRCodeWriter.encode].
     *
     * @return The current QRCode object
     */
    fun withHint(hintType: EncodeHintType, value: Any): QRCode {
        hints[hintType] = value
        return this
    }

    /**
     * Returns a [Bitmap] without creating a [File] first.
     *
     * @return [Bitmap] of this QRCode
     */
    fun bitmap(): Bitmap {
        try {
            return MatrixToImageWriter.toBitmap(createMatrix(text), matrixToImageConfig)
        } catch (e: WriterException) {
            throw QRGenerationException(
                "Failed to create QR image from text due to underlying exception", e
            )
        }
    }

    override fun file(): File {
        try {
            val file = createTempFile()
            MatrixToImageWriter.writeToFile(
                createMatrix(text), imageType, file, matrixToImageConfig
            )
            return file
        } catch (e: Exception) {
            throw QRGenerationException(
                "Failed to create QR image from text due to underlying exception", e
            )
        }
    }

    override fun file(name: String): File {
        try {
            val file = createTempFile(name)
            MatrixToImageWriter.writeToFile(
                createMatrix(text), imageType, file, matrixToImageConfig
            )
            return file
        } catch (e: Exception) {
            throw QRGenerationException(
                "Failed to create QR image from text due to underlying exception", e
            )
        }
    }

    override fun writeToStream(stream: OutputStream) {
        try {
            MatrixToImageWriter.writeToStream(
                createMatrix(text), imageType, stream, matrixToImageConfig
            )
        } catch (e: Exception) {
            when (e) {
                is IOException, is WriterException -> throw e
                else -> throw QRGenerationException(
                    "Failed to create QR image from text due to underlying exception", e
                )
            }
        }
    }
}