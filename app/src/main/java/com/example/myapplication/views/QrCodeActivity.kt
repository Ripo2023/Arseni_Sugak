package com.example.myapplication.views

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityQrCodeBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.encoder.QRCode
import com.journeyapps.barcodescanner.BarcodeEncoder

class QrCodeActivity : AppCompatActivity() {
    lateinit var binding: ActivityQrCodeBinding

    companion object{
        lateinit var data: String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createQrCode(data)

        binding.back.setOnClickListener {
            finish()
        }
    }

    //function to convert text to qr
    private fun createQrCode(data: String) {
        val barcodeEncoder = BarcodeEncoder()
        val bitmap: Bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 512, 512)
        binding.qr.setImageBitmap(bitmap)
    }
}