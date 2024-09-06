package com.example.pictures.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import com.example.pictures.model.DAOs.PictureEntityDAO
import com.example.pictures.model.Entities.PictureEntity

@Database(
    entities = [PictureEntity::class],
    version = 1
    )
@TypeConverters(BitmapImageConverter::class)
abstract class MainDb : RoomDatabase() {
    abstract val pictureEntityDAO : PictureEntityDAO

    companion object{
        fun getInstance(context: Context) : MainDb{
            return Room.databaseBuilder(
                context,
                MainDb::class.java,
                "pictures_db_2")
                .build()
        }
    }
}