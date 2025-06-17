package apps.qr.generated.core

import apps.qr.generated.core.exception.QRGenerationException
import apps.qr.generated.core.image.ImageType
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.Writer
import com.google.zxing.common.BitMatrix
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.OutputStream
import java.util.EnumMap

abstract class AbstractQRCode {
    protected val hints: MutableMap<EncodeHintType, Any> = EnumMap(EncodeHintType::class.java)

    protected lateinit var qrWriter: Writer

    protected var width: Int = 125

    protected var height: Int = 125

    protected var imageType: ImageType = ImageType.PNG

    abstract fun file(): File

    abstract fun file(name: String): File

    fun stream(): ByteArrayOutputStream {
        val stream = ByteArrayOutputStream()
        try {
            writeToStream(stream)
        } catch (e: Exception) {
            throw QRGenerationException("Không thể tạo hình ảnh QR từ văn bản do lỗi nội tại", e)
        }
        return stream
    }

    fun writeTo(stream: OutputStream) {
        try {
            writeToStream(stream)
        } catch (e: Exception) {
            throw QRGenerationException("Không thể tạo hình ảnh QR từ văn bản do lỗi nội tại", e)
        }
    }

    protected abstract fun writeToStream(stream: OutputStream)

    fun createMatrix(text: String): BitMatrix {
        return qrWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints)
    }

    protected fun createTempFile(): File {
        val file = File.createTempFile("QRCode", ".${imageType.name.lowercase()}")
        file.deleteOnExit()
        return file
    }

    protected fun createTempFile(name: String): File {
        val file = File.createTempFile(name, ".${imageType.name.lowercase()}")
        file.deleteOnExit()
        return file
    }
}