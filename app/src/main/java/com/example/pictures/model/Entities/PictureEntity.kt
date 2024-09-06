package com.example.pictures.model.Entities

import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "picture_table")
data class PictureEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    var image: ImageBitmap,
    var title: String
)