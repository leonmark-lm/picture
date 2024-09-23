package com.example.pictures.features.view.HomePage

import com.example.pictures.features.AppViewModelProvider
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pictures.R
import com.example.pictures.core.data.models.Picture
import com.example.pictures.features.navigation.NavDestination

object HomeScreenNavDestination : NavDestination(){
    override val route: String = "home"

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navToAddPictureScreen: () -> Unit,
               navToPictureScreen: (Int) -> Unit,
    viewModel: MainScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)){
    val images = viewModel.homeUiState.collectAsState()

    Column{
        MainTopBar(navToAddPictureScreen)
        PictureList(images, navToPictureScreen)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(navToAddPictureScreen: () -> Unit){
    TopAppBar(title= { Text(stringResource(R.string.app_name), fontSize = 22.sp) },
        actions={
            IconButton(onClick = navToAddPictureScreen) {
                Icon(Icons.Filled.AddCircle, contentDescription = stringResource(R.string.empty_string))
            }
        })
}

@Composable
fun PictureList(homeUiState: State<HomeUiState>, navToPictureScreen: (Int) -> Unit){
    LazyVerticalStaggeredGrid(
        modifier = Modifier.padding(5.dp),
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalItemSpacing = 5.dp

    ){
        items(items = homeUiState.value.picturesList){
                picture -> PictureCard(picture, navToPictureScreen)
        }
    }
}

@Composable
fun PictureCard(
    picture: Picture, navToPictureScreen: (Int) -> Unit
){
    Card(
        modifier = Modifier
            .height(200.dp)
            .clickable {
                navToPictureScreen(picture.id!!)
            },
        shape = RoundedCornerShape(15.dp),

        ) {
        Box() {
            picture.image?.let {
                Image(modifier = Modifier.fillMaxSize(),
                    bitmap = it,
                    contentDescription = stringResource(R.string.empty_string),
                    contentScale = ContentScale.Crop)
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 30f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                picture.title?.let {
                    Text(it, style = TextStyle(color = Color.White, fontSize = 16.sp))
                }

            }
        }
    }
}


