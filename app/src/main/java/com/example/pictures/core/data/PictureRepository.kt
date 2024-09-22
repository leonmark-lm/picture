package com.example.pictures.core.data

import com.example.pictures.core.data.models.Picture
import kotlinx.coroutines.flow.Flow

interface PictureRepository {
    fun getAllPicturesStream(): Flow<List<Picture>>

    fun getPictureStreamById(id: Int): Flow<Picture?>

    suspend fun insertPicture(picture: Picture)

    suspend fun deletePicture(picture: Picture)

    suspend fun updatePicture(picture: Picture)
}