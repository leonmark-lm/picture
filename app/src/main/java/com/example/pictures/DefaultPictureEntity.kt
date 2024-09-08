package com.example.pictures

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.example.pictures.R
import com.example.pictures.model.Entities.PictureEntity

@Composable
fun getDefaultPicture() = PictureEntity(
    null,
    ImageBitmap.imageResource(id = R.drawable.no_image),
    R.string.not_load_string_data.toString()
)
