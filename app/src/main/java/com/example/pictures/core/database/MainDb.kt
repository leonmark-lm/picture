package com.example.pictures.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pictures.core.database.utils.BitmapImageConverter
import com.example.pictures.core.database.DAOs.PictureDAO
import com.example.pictures.core.database.models.PictureDBO

@Database(
    entities = [PictureDBO::class],
    version = 1
    )
@TypeConverters(BitmapImageConverter::class)
abstract class MainDb : RoomDatabase() {
    abstract val pictureDAO : PictureDAO
}