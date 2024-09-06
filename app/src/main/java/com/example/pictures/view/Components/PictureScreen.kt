package com.example.pictures.view.Components

import android.app.Activity
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pictures.model.Entities.PictureEntity

@Composable
fun PictureScreen(picture: PictureEntity){
    val context = LocalContext.current
    val activity = if (context is Activity) context else null
    Column (modifier = Modifier
        .fillMaxSize()
        .padding(0.dp, 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start) {
            Text(picture.title, fontSize=25.sp)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ){

            val openDialog = remember { mutableStateOf(false) }
            Image(modifier = Modifier
                .fillMaxSize()
                .clickable {
                    openDialog.value = true
                },
                bitmap = picture.image,
                contentDescription = "",
                contentScale = ContentScale.Crop)

            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {},
                    title = {  },
                    text = {  },
                    confirmButton = {
                        Column(verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.End){
                            Button(onClick = { openDialog.value = false }) {
                                Icon(Icons.Filled.Close, contentDescription = "")
                            }
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth().height(600.dp),
                            shape = RoundedCornerShape(15.dp)
                            ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize(),
                                bitmap = picture.image,
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                        }


                    },
                    containerColor = Color.Transparent
                )
            }
        }
        Row {
            Button(onClick = {
                activity!!.finish()
            }) {
                Text("Go to the main page")
            }
        }

    }

}
