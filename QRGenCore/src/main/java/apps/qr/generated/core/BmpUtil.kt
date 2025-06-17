package apps.qr.generated.core

import android.graphics.Bitmap
import android.util.Log
import java.io.OutputStream
import java.nio.ByteBuffer

object BmpUtil {
    private const val BMP_WIDTH_OF_TIMES = 4
    private const val BYTE_PER_PIXEL = 3

    /**
     * Android Bitmap Object to Window's v3 24bit Bmp Format File
     * @param image The Bitmap to save
     * @param stream The OutputStream to write to
     * @return true if save is successful, false otherwise
     */
    fun save(image: Bitmap?, stream: OutputStream?): Boolean {
        val start = System.currentTimeMillis()

        if (image == null || stream == null) {
            return false
        }

        val isSaveSuccess = true

        // Image size
        val width = image.width
        val height = image.height

        // Image dummy data size
        // Reason: the amount of bytes per image row must be a multiple of 4 (requirements of BMP format)
        var dummyBytesPerRow: ByteArray? = null
        var hasDummy = false
        val rowWidthInBytes =
            BYTE_PER_PIXEL * width // Source image width * number of bytes to encode one pixel
        if (rowWidthInBytes % BMP_WIDTH_OF_TIMES > 0) {
            hasDummy = true
            // The number of dummy bytes we need to add on each row
            dummyBytesPerRow =
                ByteArray(BMP_WIDTH_OF_TIMES - (rowWidthInBytes % BMP_WIDTH_OF_TIMES)) { 0xFF.toByte() }
        }

        // An array to receive the pixels from the source image
        val pixels = IntArray(width * height)

        // The number of bytes used in the file to store raw image data (excluding file headers)
        val imageSize =
            (rowWidthInBytes + (if (hasDummy) dummyBytesPerRow?.size ?: 0 else 0)) * height
        // File headers size
        val imageDataOffset = 0x36

        // Final size of the file
        val fileSize = imageSize + imageDataOffset

        // Android Bitmap Image Data
        image.getPixels(pixels, 0, width, 0, 0, width, height)

        // Allocate ByteBuffer for the file
        val buffer = ByteBuffer.allocate(fileSize)

        /**
         * BITMAP FILE HEADER Write Start
         */
        buffer.put(0x42.toByte()) // 'B'
        buffer.put(0x4D.toByte()) // 'M'

        // Size
        buffer.put(writeInt(fileSize))

        // Reserved
        buffer.put(writeShort(0))
        buffer.put(writeShort(0))

        // Image data start offset
        buffer.put(writeInt(imageDataOffset))

        /**
         * BITMAP INFO HEADER Write Start
         */
        // Size
        buffer.put(writeInt(0x28))

        // Width, height
        // If we add 3 dummy bytes per row: it means we add a pixel (and the image width is modified)
        buffer.put(writeInt(width + if (hasDummy && dummyBytesPerRow?.size == 3) 1 else 0))
        buffer.put(writeInt(height))

        // Planes
        buffer.put(writeShort(1))

        // Bit count
        buffer.put(writeShort(24))

        // Bit compression
        buffer.put(writeInt(0))

        // Image data size
        buffer.put(writeInt(imageSize))

        // Horizontal resolution in pixels per meter
        buffer.put(writeInt(0))

        // Vertical resolution in pixels per meter (unreliable)
        buffer.put(writeInt(0))

        buffer.put(writeInt(0))
        buffer.put(writeInt(0))

        /** BITMAP INFO HEADER Write End */

        var row = height
        var startPosition = (row - 1) * width
        var endPosition = row * width
        while (row > 0) {
            for (i in startPosition until endPosition) {
                buffer.put((pixels[i] and 0x000000FF).toByte())
                buffer.put(((pixels[i] and 0x0000FF00) shr 8).toByte())
                buffer.put(((pixels[i] and 0x00FF0000) shr 16).toByte())
            }
            if (hasDummy) {
                dummyBytesPerRow?.let { buffer.put(it) }
            }
            row--
            endPosition = startPosition
            startPosition -= width
        }

        stream.write(buffer.array())
        stream.close()

        Log.v("BmpUtil", "${System.currentTimeMillis() - start} ms")

        return isSaveSuccess
    }

    /**
     * Write integer to little-endian
     * @param value The integer value to write
     * @return Byte array in little-endian format
     */
    private fun writeInt(value: Int): ByteArray {
        return byteArrayOf(
            (value and 0x000000FF).toByte(),
            ((value and 0x0000FF00) shr 8).toByte(),
            ((value and 0x00FF0000) shr 16).toByte(),
            ((value and 0xFF000000.toInt()) shr 24).toByte()
        )
    }

    /**
     * Write short to little-endian byte array
     * @param value The short value to write
     * @return Byte array in little-endian format
     */
    private fun writeShort(value: Short): ByteArray {
        return byteArrayOf(
            (value.toInt() and 0x00FF).toByte(), ((value.toInt() and 0xFF00) shr 8).toByte()
        )
    }
}