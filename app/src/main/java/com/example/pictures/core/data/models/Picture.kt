package com.example.pictures.core.data.models

import androidx.compose.ui.graphics.ImageBitmap

data class Picture (
    val id : Int? = null,
    var image: ImageBitmap?,
    var title: String?
)