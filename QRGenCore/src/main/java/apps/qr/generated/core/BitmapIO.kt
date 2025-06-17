package apps.qr.generated.core

import android.graphics.Bitmap
import apps.qr.generated.core.image.ImageType
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object BitmapIO {
    fun write(image: Bitmap, type: ImageType, stream: OutputStream): Boolean {
        return when (type) {
            ImageType.PNG -> image.compress(Bitmap.CompressFormat.PNG, 80, stream)
            ImageType.JPG -> image.compress(Bitmap.CompressFormat.JPEG, 80, stream)
            else -> BmpUtil.save(image, stream) // Default should be Bitmap for Android
        }
    }

    fun write(image: Bitmap, type: ImageType, file: File): Boolean {
        return FileOutputStream(file).use { stream ->
            try {
                write(image, type, stream)
            } finally {
                stream.flush()
            }
        }
    }
}