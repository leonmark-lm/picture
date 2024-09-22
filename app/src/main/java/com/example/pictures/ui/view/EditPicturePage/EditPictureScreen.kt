package com.example.pictures.ui.view.EditPicturePage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pictures.R
import com.example.pictures.core.data.models.Picture
import com.example.pictures.ui.navigation.NavDestination
import com.example.pictures.ui.view.PicturePage.PictureUIState

object EditPictureScreenNavDestination : NavDestination(){
    override val route: String = "edit_picture_screen"
    const val itemIdArg = "id"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun EditPictureScreen(
    navToPreviousScreen: () -> Unit,
    viewModel: EditPictureViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val context = LocalContext.current

    val pictureUIState = viewModel.pictureUIState.collectAsState()
    val newPictureTitleState = remember { mutableStateOf<String?>(null) }
    val newPictureImageState = remember { mutableStateOf<ImageBitmap?>(null) }

    val imageUriState = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ){uri: Uri? ->
        imageUriState.value = uri
    }
    imageUriState.value?.let {
        newPictureImageState.value = getBitmapByUri(context, it).asImageBitmap()
    }

    Column {
        EditPictureTopBar(
            navToPreviousScreen = navToPreviousScreen,
            onSave = {
                with(viewModel){
                    editedPictureState.value.image = newPictureImageState.value
                    editedPictureState.value.title = newPictureTitleState.value
                    update()
                }
                navToPreviousScreen()
            },
            onLoadImage = {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        )

        InputHost(pictureUIState, newPictureTitleState, newPictureImageState)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPictureTopBar(navToPreviousScreen: () -> Unit,
                      onLoadImage: () -> Unit,
                      onSave: () -> Unit){
    TopAppBar(title= {
        IconButton(onClick = navToPreviousScreen) {
            Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.empty_string))
        }
        },
        actions={
            IconButton(onClick = onLoadImage) {
                Icon(Icons.Filled.AccountBox, contentDescription = stringResource(R.string.empty_string))

            }
            IconButton(onClick = onSave) {
                Icon(Icons.Filled.Done, contentDescription = stringResource(R.string.empty_string))
            }

        })
}

@Composable
fun InputHost(selectedPictureUIState: State<EditPictureUiState>,
              newPictureTitleState: MutableState<String?>,
              newPictureImageState: MutableState<ImageBitmap?>
){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Card (modifier = Modifier.size(300.dp, 500.dp),
            shape = RoundedCornerShape(15.dp)
        ){
            if (newPictureImageState.value != null){
                Image(newPictureImageState.value!!,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = stringResource(R.string.empty_string),
                    contentScale = ContentScale.Crop
                )
            }else if (selectedPictureUIState.value.picture.image != null){
                Image(selectedPictureUIState.value.picture.image!!,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = stringResource(R.string.empty_string),
                    contentScale = ContentScale.Crop
                )
            }
        }

        TextField(value = newPictureTitleState.value ?: selectedPictureUIState.value.picture.title
        ?: stringResource(R.string.empty_string),
            onValueChange = {newPictureTitleState.value = it},
            singleLine = true)
    }
}

fun getBitmapByUri(context: Context, uri: Uri): Bitmap {
    val inputStream = context.contentResolver.openInputStream(uri)
    val bitmap = BitmapFactory.decodeStream(inputStream)
    inputStream?.close()
    return bitmap
}