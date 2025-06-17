package apps.qr.generated.core

import android.graphics.Bitmap
import androidx.core.graphics.createBitmap
import apps.qr.generated.core.image.ImageType
import com.google.zxing.common.BitMatrix
import java.io.File
import java.io.IOException
import java.io.OutputStream

object MatrixToImageWriter {
    private val DEFAULT_CONFIG = MatrixToImageConfig.Config()

    /**
     * Renders a [BitMatrix] as an image, where "false" bits are rendered
     * as white, and "true" bits are rendered as black. Uses default configuration.
     *
     * @param matrix [BitMatrix] to write
     * @return [Bitmap] representation of the input
     */
    private fun toBitmap(matrix: BitMatrix): Bitmap {
        return toBitmap(matrix, DEFAULT_CONFIG)
    }

    /**
     * As [toBitmap], but allows customization of the output.
     *
     * @param matrix [BitMatrix] to write
     * @param config Output configuration
     * @return [Bitmap] representation of the input
     */
    fun toBitmap(matrix: BitMatrix, config: MatrixToImageConfig.Config): Bitmap {
        val onColor = config.pixelOnColor
        val offColor = config.pixelOffColor
        val width = matrix.width
        val height = matrix.height
        val pixels = IntArray(width * height)

        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (matrix[x, y]) onColor else offColor
            }
        }

        val image = createBitmap(width, height, config.bufferedImageColorModel)
        image.setPixels(pixels, 0, width, 0, 0, width, height)
        return image
    }

    /**
     * Writes a [BitMatrix] to a file.
     *
     * @see [writeToFile]
     */
    @Throws(IOException::class)
    fun writeToFile(matrix: BitMatrix, format: ImageType, file: File) {
        writeToFile(matrix, format, file, DEFAULT_CONFIG)
    }

    /**
     * As [writeToFile], but allows customization of the output.
     *
     * @param matrix [BitMatrix] to write
     * @param format Image format (e.g., PNG, JPG)
     * @param file File to write to
     * @param config Output configuration
     */
    @Throws(IOException::class)
    fun writeToFile(
        matrix: BitMatrix, format: ImageType, file: File, config: MatrixToImageConfig.Config
    ) {
        val image = toBitmap(matrix, config)
        if (!BitmapIO.write(image, format, file)) {
            throw IOException("Could not write an image of format $format to $file")
        }
    }

    /**
     * Writes a [BitMatrix] to a stream.
     *
     * @see [writeToStream]
     */
    @Throws(IOException::class)
    fun writeToStream(matrix: BitMatrix, format: ImageType, stream: OutputStream) {
        writeToStream(matrix, format, stream, DEFAULT_CONFIG)
    }

    /**
     * As [writeToStream], but allows customization of the output.
     *
     * @param matrix [BitMatrix] to write
     * @param format Image format (e.g., PNG, JPG)
     * @param stream OutputStream to write to
     * @param config Output configuration
     */
    @Throws(IOException::class)
    fun writeToStream(
        matrix: BitMatrix,
        format: ImageType,
        stream: OutputStream,
        config: MatrixToImageConfig.Config
    ) {
        val image = toBitmap(matrix, config)
        if (!BitmapIO.write(image, format, stream)) {
            throw IOException("Could not write an image of format $format")
        }
    }
}