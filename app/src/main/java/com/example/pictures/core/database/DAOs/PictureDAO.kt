package com.example.pictures.core.database.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pictures.core.database.models.PictureDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface PictureDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pictureDBO: PictureDBO)

    @Delete
    suspend fun delete(pictureDBO: PictureDBO)

    @Query("SELECT * FROM picture_table")
    fun getAllPicturesStream() : Flow<List<PictureDBO>>

    @Query("SELECT * FROM picture_table WHERE id = :id")
    fun getPictureByIdStream(id: Int) : Flow<PictureDBO?>

}