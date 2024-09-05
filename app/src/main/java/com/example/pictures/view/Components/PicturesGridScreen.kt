package com.example.pictures.view.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pictures.viewmodel.getImages


@Composable
fun PicturesGridScreen(){
    //--------------

    /*val images : List<Picture> = listOf(
        Picture(0, ImageBitmap.imageResource(id = R.drawable.dubai_tower), "Dubai Tower"),
        Picture(1, ImageBitmap.imageResource(id = R.drawable.lahta), "Lahta Tower"),
        Picture(2, ImageBitmap.imageResource(id = R.drawable.dubai_tower), "Dubai Tower"),
        Picture(3, ImageBitmap.imageResource(id = R.drawable.lahta), "Lahta Tower")
    )*/
    //--------------

    val images = getImages()

    LazyVerticalStaggeredGrid(
        modifier = Modifier.padding(5.dp),
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalItemSpacing = 5.dp

    ){
        items(items = images){
            picture -> PictureCard(picture)
        }
    }

}


