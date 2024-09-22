package com.example.pictures.core.data.utils

import com.example.pictures.core.data.models.Picture
import com.example.pictures.core.database.models.PictureDBO

fun PictureDBO.toPicture(): Picture {
    return Picture(
        id = id,
        image = image,
        title = title
    )
}

fun Picture.toPictureDBO(): PictureDBO {
    return PictureDBO(
        id = id,
        image = image,
        title = title
    )
}