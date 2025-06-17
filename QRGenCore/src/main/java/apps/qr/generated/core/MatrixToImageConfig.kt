package apps.qr.generated.core

import android.graphics.Bitmap

object MatrixToImageConfig {
    const val BLACK = 0xFF000000.toInt()
    const val WHITE = 0xFFFFFFFF.toInt()

    data class Config(
        val onColor: Int = BLACK,
        val offColor: Int = WHITE
    ) {
        /**
         * Returns the pixel color for "on" pixels, specified as an ARGB value.
         */
        val pixelOnColor: Int
            get() = onColor

        /**
         * Returns the pixel color for "off" pixels, specified as an ARGB value.
         */
        val pixelOffColor: Int
            get() = offColor

        /**
         * Returns the Bitmap color model for Android.
         * Note: ALPHA_8 model is not used as it results in empty files and IOExceptions.
         */
        val bufferedImageColorModel: Bitmap.Config
            get() = Bitmap.Config.ARGB_8888
    }
}