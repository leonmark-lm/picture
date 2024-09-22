package com.example.pictures.ui.view.PicturePage

import AppViewModelProvider
import android.app.Activity
import android.content.Intent
import android.text.Editable.Factory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Delete
import com.example.pictures.R
import com.example.pictures.core.data.models.Picture
import com.example.pictures.core.database.models.PictureDBO
import com.example.pictures.getDefaultPicture
import com.example.pictures.ui.navigation.NavDestination

object PictureScreenNavDestination : NavDestination(){
    override val route: String = "picture_screen"
    const val itemIdArg = "id"
    val routeWithArgs = "$route/{$itemIdArg}"
}
@Composable
fun PictureScreen(navToEditPictureScreen: (Int) -> Unit,
                  navToHomeScreen: () -> Unit,
                  viewModel: PictureViewModel = viewModel(factory = AppViewModelProvider.Factory)){

    val pictureUIState = viewModel.pictureUIState.collectAsState()

    Column {
        PictureTopBar(pictureUIState, navToEditPictureScreen, navToHomeScreen){
            viewModel.deletePicture()
            navToHomeScreen()
        }
        PictureHost(pictureUIState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PictureTopBar(pictureUIState: State<PictureUIState>,
                  navToEditPictureScreen: (Int) -> Unit,
                  navToHomeScreen: () -> Unit,
                  onDeletePicture: () -> Unit){
    val isOpenDialog = remember { mutableStateOf(false) }

    TopAppBar(title= {
        IconButton(onClick = {
            navToHomeScreen()
        }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.empty_string))
        }
    },
        actions={
            IconButton(onClick = {
                navToEditPictureScreen(pictureUIState.value.picture.id!!)
            }) {
                Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.empty_string))

            }
            IconButton(onClick = {
                isOpenDialog.value = true
            }) {
                Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.empty_string))
            }

            if (isOpenDialog.value){
                DeletingCofirmAlertDialog(
                    onDelete = {
                        isOpenDialog.value = false
                        onDeletePicture()
                               },
                    onDismissRequest = {
                        isOpenDialog.value = false
                    }
                )
            }


        })
}

@Composable
fun DeletingCofirmAlertDialog(onDelete: () -> Unit,
                              onDismissRequest: () -> Unit){
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.confirm_action)) },
        text = { Text(stringResource(R.string.confirm_question)) },
        confirmButton = {
            Button(onDelete) {
                Text(stringResource(R.string.answer_yes), fontSize = 22.sp)
            }
        }
    )
}

@Composable
fun PictureHost(pictureUIState: State<PictureUIState>){
    val isOpenDialog = remember { mutableStateOf(false) }

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(0.dp, 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start) {
            Text(pictureUIState.value.picture.title ?: "", fontSize=25.sp)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ){
            Image(modifier = Modifier
                .fillMaxSize()
                .clickable {
                    isOpenDialog.value = true

                },
                bitmap = pictureUIState.value.picture.image ?: getDefaultPicture().image!!,
                contentDescription = stringResource(R.string.empty_string),
                contentScale = ContentScale.Crop)

            if (isOpenDialog.value) {
                PictureHost(pictureUIState){
                    isOpenDialog.value = false
                }
            }
        }

    }
}

@Composable
fun PictureHost(pictureUIState: State<PictureUIState>,
                       onClose: () -> Unit){
    AlertDialog(
        onDismissRequest = {},
        title = {},
        text = {},
        confirmButton = {
            Column(verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End){
                Button(onClick = onClose) {
                    Icon(Icons.Filled.Close,
                        contentDescription = stringResource(R.string.empty_string))
                }
            }
            PictureCard(pictureUIState)
        },
        containerColor = Color.Transparent
    )
}

@Composable
fun PictureCard(pictureUIState: State<PictureUIState>){
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { scaleChange, offsetChange, rotationChange ->
        scale *= scaleChange
        rotation += rotationChange
        offset += offsetChange
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    rotationZ = rotation
                    translationX = offset.x
                    translationY = offset.y
                }
                .transformable(state),
            bitmap = pictureUIState.value.picture.image!!,
            contentDescription = stringResource(R.string.empty_string),
            contentScale = ContentScale.Inside
        )
    }
}

