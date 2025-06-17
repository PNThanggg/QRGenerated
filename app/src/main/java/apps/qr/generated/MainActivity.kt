package apps.qr.generated

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import apps.qr.generated.core.QRCode
import apps.qr.generated.core.image.ImageType
import apps.qr.generated.core.scheme.EMail
import apps.qr.generated.core.scheme.MMS
import apps.qr.generated.core.scheme.SMS
import apps.qr.generated.core.scheme.VCard
import apps.qr.generated.databinding.ActivityMainBinding
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Override the image type to be JPG
        QRCode.from("Hello World").to(ImageType.JPG).file()
        QRCode.from("Hello World").to(ImageType.JPG).stream()

        // Override image size to be 250x250
        QRCode.from("Hello World").withSize(250, 250).file()
        QRCode.from("Hello World").withSize(250, 250).stream()

        // Override size and image type
        QRCode.from("Hello World").to(ImageType.GIF).withSize(250, 250).file()
        QRCode.from("Hello World").to(ImageType.GIF).withSize(250, 250).stream()

        // Override default colors (black on white)
        // Format: 0xAARRGGBB
        QRCode.from("Hello World").withColor(0xFFFF0000.toInt(), 0xFFFFFFAA.toInt()).file()

        // Supply own outputstream
        QRCode.from("Hello World").to(ImageType.PNG).writeTo(ByteArrayOutputStream())

        // Supply own file name
        QRCode.from("Hello World").file("QRCode")

        // Supply charset hint to ZXING
        QRCode.from("Hello World").withCharset("UTF-8")

        // Supply error correction level hint to ZXING
        QRCode.from("Hello World").withErrorCorrection(ErrorCorrectionLevel.L)

        // Supply any hint to ZXING
        QRCode.from("Hello World").withHint(EncodeHintType.CHARACTER_SET, "UTF-8")

        // Encode contact data as vCard using defaults
        val johnDoe = VCard(
            name = "John Doe",
            email = "john.doe@example.org",
            address = "John Doe Street 1, 5678 Doestown",
            title = "Mister",
            company = "John Doe Inc.",
            website = "www.example.org",
            phoneNumber = "1234",
        )
        QRCode.from(johnDoe).file()

        // Encode email data
        val email = EMail("John.Doe@example.org")
        QRCode.from(email).file()

        // Encode MMS data
        val mms = MMS("8675309", "Hello Jenny")
        QRCode.from(mms).file()

        // Encode SMS data
        val sms = SMS("8675309", "Hello Jenny")
        QRCode.from(sms).file()

        val bitmap: Bitmap = QRCode.from(sms).withSize(250, 250).bitmap()
        binding.imageView.setImageBitmap(bitmap)
    }
}