package com.example.pictures.view.Components

import android.app.Activity
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.example.pictures.model.Entities.PictureEntity
import com.example.pictures.view.NewPictureActivity
import com.example.pictures.viewmodels.PicturesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PictureTopBar(picture: PictureEntity, viewModel: PicturesViewModel){
    val context = LocalContext.current
    val activity = if(context is Activity) context else null
    val openDialog = remember { mutableStateOf(false) }

    TopAppBar(title= { Text("Picture", fontSize = 22.sp) },
        actions={
            IconButton(onClick = {
                val intent = Intent(context, NewPictureActivity::class.java)
                intent.putExtra("id", picture.id)
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
                            viewModel.delete(picture)
                            activity?.finish()
                        }) {
                            Text("Да", fontSize = 22.sp)
                        }
                    }
                )
            }
        })
}