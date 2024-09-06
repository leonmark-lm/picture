package com.example.pictures.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class BitmapImageConverter {

    @TypeConverter
    fun fromBitmapImage(bitmap: ImageBitmap) : ByteArray{
        val outputStream = ByteArrayOutputStream()
        bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        outputStream.close()
        return byteArray
    }

    @TypeConverter
    fun toBitmapImage(byteArray: ByteArray) : ImageBitmap{
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size).asImageBitmap()
    }
}