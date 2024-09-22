package com.example.pictures.core.data

import com.example.pictures.core.data.models.Picture
import com.example.pictures.core.data.utils.toPicture
import com.example.pictures.core.data.utils.toPictureDBO
import com.example.pictures.core.database.DAOs.PictureDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DBPictureRepository(val pictureDAO: PictureDAO) : PictureRepository{

    override fun getAllPicturesStream(): Flow<List<Picture>> =
        pictureDAO
            .getAllPicturesStream().map {
                dboList -> dboList.map {
                    it.toPicture() }}

    override fun getPictureStreamById(id: Int): Flow<Picture?> =
        pictureDAO
            .getPictureByIdStream(id).map {
                it?.toPicture()
            }

    override suspend fun insertPicture(picture: Picture){
        pictureDAO.insert(picture.toPictureDBO())
    }

    override suspend fun deletePicture(picture: Picture){
        pictureDAO.delete(picture.toPictureDBO())
    }

    override suspend fun updatePicture(picture: Picture){
        pictureDAO.insert(picture.toPictureDBO())
    }
}