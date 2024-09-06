package com.example.pictures.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.example.pictures.R
import com.example.pictures.model.Entities.PictureEntity

@Composable
fun getImages() : List<PictureEntity>  = listOf(
        PictureEntity(0, ImageBitmap.imageResource(id = R.drawable.dubai_tower), "Dubai Tower"),
        PictureEntity(1, ImageBitmap.imageResource(id = R.drawable.lahta), "Lahta Tower"),
        PictureEntity(2, ImageBitmap.imageResource(id = R.drawable.world_trade_center), "World Trade Center"),
        PictureEntity(3, ImageBitmap.imageResource(id = R.drawable.shanghai_tower), "Shanghai Tower")
    )

@Composable
fun getImageById(id : Int) : PictureEntity? {
    var images = getImages()
    for (image in images){
        if (image.id == id) return image
    }
    return null
}