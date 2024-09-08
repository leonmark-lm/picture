package com.example.pictures.view.PicturePage.Components

import android.app.Activity
import android.graphics.Picture
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pictures.R
import com.example.pictures.getDefaultPicture
import com.example.pictures.model.Entities.PictureEntity
import com.example.pictures.viewmodels.PicturesViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@Composable
fun PictureScreen(id: Int, viewModel: PicturesViewModel){
    val picture = viewModel.getById(id).collectAsState(initial = getDefaultPicture())
    val isOpenDialog = remember { mutableStateOf(false) }

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(0.dp, 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start) {
            Text(picture.value!!.title!!, fontSize=25.sp)
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
                bitmap = picture.value!!.image!!,
                contentDescription = stringResource(R.string.empty_string),
                contentScale = ContentScale.Crop)

            if (isOpenDialog.value) {
                AlertDialog(
                    onDismissRequest = {},
                    title = {  },
                    text = {  },
                    confirmButton = {
                        Column(verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.End){
                            Button(onClick = { isOpenDialog.value = false }) {
                                Icon(Icons.Filled.Close,
                                    contentDescription = stringResource(R.string.empty_string))
                            }
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(600.dp),
                            shape = RoundedCornerShape(15.dp)
                            ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize(),
                                bitmap = picture.value!!.image!!,
                                contentDescription = stringResource(R.string.empty_string),
                                contentScale = ContentScale.Crop
                            )
                        }
                    },
                    containerColor = Color.Transparent
                )
            }
        }

    }

}
