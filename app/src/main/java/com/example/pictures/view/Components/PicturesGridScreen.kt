package com.example.pictures.view.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pictures.viewmodels.PicturesViewModel


@Composable
fun PicturesGridScreen(viewModel: PicturesViewModel){
    val images = viewModel.items.collectAsState(initial = emptyList())
    LazyVerticalStaggeredGrid(
        modifier = Modifier.padding(5.dp),
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalItemSpacing = 5.dp

    ){
        items(items = images.value){
            picture -> PictureCard(picture)
        }
    }

}


