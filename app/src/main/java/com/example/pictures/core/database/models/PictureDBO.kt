package com.example.pictures.core.database.models

import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "picture_table")
data class PictureDBO (
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    var image: ImageBitmap?,
    var title: String?
)