package com.example.pictures.core.data

import com.example.pictures.core.database.DBProvider

object RepositoryProvider {

    val dbPictureRepository by lazy { DBPictureRepository(DBProvider.database.pictureDAO) }
}