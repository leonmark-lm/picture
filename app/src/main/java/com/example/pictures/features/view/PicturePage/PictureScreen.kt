package com.example.pictures.features.view.PicturePage

import com.example.pictures.features.AppViewModelProvider
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pictures.R
import com.example.pictures.features.navigation.NavDestination

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
    val context = LocalContext.current

    Column {
        PictureTopBar(pictureUIState,
            navToEditPictureScreen, 
            navToHomeScreen,
            {
                pictureUIState.value.picture.image?.let {
                    viewModel.sharePicture(context, it.asAndroidBitmap())
                }
            })
        {
            viewModel.deletePicture()
            navToHomeScreen()
        }
        PictureCard(pictureUIState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PictureTopBar(pictureUIState: State<PictureUIState>,
                  navToEditPictureScreen: (Int) -> Unit,
                  navToHomeScreen: () -> Unit,
                  onShare:() -> Unit,
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
                onShare()
            }) {
                Icon(Icons.Filled.Share, contentDescription = stringResource(R.string.empty_string))
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
fun PictureCard(pictureUIState: State<PictureUIState>){
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(10.dp),
        ) {
        Box(){
            pictureUIState.value.picture.image?.let{
                Image(modifier = Modifier.fillMaxSize(),
                    bitmap = it,
                    contentDescription = stringResource(R.string.empty_string),
                    contentScale = ContentScale.Fit)
            }
            pictureUIState.value.picture.title?.let{
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(it, style = TextStyle(color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.SemiBold))
                }
            }
        }
    }
}




