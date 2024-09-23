package com.example.pictures.features.view.AddPicturePage

import com.example.pictures.features.AppViewModelProvider
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.example.pictures.features.navigation.NavDestination

object AddPictureScreenNavDestination : NavDestination(){
    override val route: String = "add_picture_screen"
    const val itemUriArg = "uri"
    val routeWithArgs = "$route/{$itemUriArg}"
}

@Composable
fun AddPictureScreen(
    navToHomeScreen: () -> Unit,
    viewModel: AddPictureViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current

    val newPictureTitleState = remember { mutableStateOf<String?>(null) }
    val newPictureImageState = remember { mutableStateOf<ImageBitmap?>(null) }

    val imageUriState = remember { mutableStateOf<Uri?>(viewModel.pictureUriFromIntent) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ){uri: Uri? ->
        imageUriState.value = uri
    }
    imageUriState.value?.let {
        newPictureImageState.value = viewModel.getBitmapByUri(context, it).asImageBitmap()
    }

    Column {
        AddPictureTopBar(
            navToPreviousScreen = navToHomeScreen,
            onSave = {
                with(viewModel){
                    newPictureState.value.title = newPictureTitleState.value ?: AddPictureViewModel.DEFAULT_TITLE_VALUE
                    newPictureState.value.image = newPictureImageState.value
                    insert()
                }
                navToHomeScreen()
            },
            onLoadImage = {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        )
        InputHost(newPictureTitleState, newPictureImageState)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPictureTopBar(navToPreviousScreen: () -> Unit,
                      onLoadImage: () -> Unit,
                      onSave: () -> Unit
){
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
fun InputHost(newPictureTitleState: MutableState<String?>,
                newPictureImageState: MutableState<ImageBitmap?>){

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp),
        contentAlignment = Alignment.Center
    ){

        Card (modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(15.dp)
        ){

            InputTitleTextField(value = newPictureTitleState, AddPictureViewModel.MAX_TITLE_LENGTH)

            newPictureImageState.value?.let {
                Image(it,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = stringResource(R.string.empty_string),
                    contentScale = ContentScale.Crop
                )
            }

        }
    }

}

@Composable
fun InputTitleTextField(value: MutableState<String?>, maxLenght: Int){
    TextField(modifier = Modifier.fillMaxWidth(),
        value = value.value ?: stringResource(R.string.empty_string),
        onValueChange = {
            if (it.length < maxLenght){
                value.value = it
            }
                        },
        placeholder = {Text(stringResource(R.string.title_text_field_hint))},
        singleLine = true)
}

