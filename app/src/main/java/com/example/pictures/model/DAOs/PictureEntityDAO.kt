package com.example.pictures.model.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverter
import com.example.pictures.model.BitmapImageConverter
import com.example.pictures.model.Entities.PictureEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PictureEntityDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pictureEntity: PictureEntity)

    @Delete
    suspend fun delete(pictureEntity: PictureEntity)

    @Query("SELECT * FROM picture_table")
    fun getAll() : Flow<List<PictureEntity>>

    @Query("SELECT * FROM picture_table WHERE id = :id")
    fun getById(id: Int) : Flow<PictureEntity?>

}