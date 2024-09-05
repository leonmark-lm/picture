package com.example.pictures.view.Components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pictures.view.NewPictureActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PictureTopBar(){
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false) }

    TopAppBar(title= { Text("Picture", fontSize = 22.sp) },
        actions={
            IconButton(onClick = {
                val intent = Intent(context, NewPictureActivity::class.java)
                context.startActivity(intent)
            }) {
                Icon(Icons.Filled.Edit, contentDescription = "Редактировать картинку")
            }
            IconButton(onClick = {
                openDialog.value = true
            }) {
                Icon(Icons.Filled.Delete, contentDescription = "Удалить картинку")
            }

            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = { openDialog.value = false},
                    title = { Text(text = "Подтверждение действия") },
                    text = { Text("Вы действительно хотите удалить выбранный элемент?") },
                    confirmButton = {
                        Button({
                            openDialog.value = false
                            //TODO
                        }) {
                            Text("OK", fontSize = 22.sp)
                        }
                    }
                )
            }
        })
}