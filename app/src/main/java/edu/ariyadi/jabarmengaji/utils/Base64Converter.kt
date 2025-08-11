package edu.ariyadi.jabarmengaji.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

object Base64Converter {

    /**
     * Mengonversi string Base64 menjadi Bitmap.
     * @param base64String String Base64 dari gambar.
     * @return Bitmap hasil konversi, atau null jika terjadi kesalahan.
     */
    fun base64ToBitmap(base64String: String?): Bitmap? {
        return try {
            if (base64String.isNullOrEmpty()) {
                return null
            }
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Mengonversi Bitmap menjadi string Base64.\
     * @param bitmap Bitmap yang akan dikonversi.
     * @return String Base64 hasil konversi, atau null jika terjadi kesalahan.
     */
    fun bitmapToBase64(bitmap: Bitmap?): String? {
        return try {
            if (bitmap == null) {
                return null
            }
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            val imageBytes = outputStream.toByteArray()
            Base64.encodeToString(imageBytes, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}