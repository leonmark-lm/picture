package com.example.pictures

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.example.pictures.core.data.models.Picture
import com.example.pictures.core.database.models.PictureDBO

@Composable
fun getDefaultPicture() = Picture(
    -1,
    ImageBitmap.imageResource(id = R.drawable.no_image),
    "Not loaded"
)
