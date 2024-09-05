package com.example.pictures.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.example.pictures.R
import com.example.pictures.model.data.Picture

@Composable
fun getImages() : List<Picture>  = listOf(
        Picture(0, ImageBitmap.imageResource(id = R.drawable.dubai_tower), "Dubai Tower"),
        Picture(1, ImageBitmap.imageResource(id = R.drawable.lahta), "Lahta Tower"),
        Picture(2, ImageBitmap.imageResource(id = R.drawable.world_trade_center), "World Trade Center"),
        Picture(3, ImageBitmap.imageResource(id = R.drawable.shanghai_tower), "Shanghai Tower")
    )

@Composable
fun getImageById(id : Int) : Picture {
    var images = getImages()
    for (image in images){
        if (image.id == id) return image
    }
    return images[0]
}