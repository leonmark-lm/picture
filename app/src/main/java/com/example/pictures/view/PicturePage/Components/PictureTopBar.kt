package com.example.pictures.view.PicturePage.Components

import android.app.Activity
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.pictures.R
import com.example.pictures.model.Entities.PictureEntity
import com.example.pictures.view.PictureAddEditPage.AddEditPictureActivity
import com.example.pictures.viewmodels.PicturesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PictureTopBar(picture: PictureEntity, viewModel: PicturesViewModel){
    val context = LocalContext.current
    val activity = if (context is Activity) context else null
    val isOpenDialog = remember { mutableStateOf(false) }

    TopAppBar(title= {
        IconButton(onClick = {
            activity!!.finish()
        }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = R.string.empty_string.toString())
        }
        },
        actions={
            IconButton(onClick = {
                Intent(context, AddEditPictureActivity::class.java).apply {
                    putExtra(R.string.intent_picture_id_identifier.toString(), picture.id)
                }.also {
                    context.startActivity(it)
                }
            }) {
                Icon(Icons.Filled.Edit, contentDescription = R.string.empty_string.toString())

            }
            IconButton(onClick = {
                isOpenDialog.value = true
            }) {
                Icon(Icons.Filled.Delete, contentDescription = R.string.empty_string.toString())
            }

            if (isOpenDialog.value) {
                AlertDialog(
                    onDismissRequest = { isOpenDialog.value = false},
                    title = { Text(R.string.confirm_action.toString()) },
                    text = { Text(R.string.confirm_question.toString()) },
                    confirmButton = {
                        Button({
                            isOpenDialog.value = false
                            viewModel.delete(picture)
                            activity!!.finish()
                        }) {
                            Text(R.string.answer_yes.toString(), fontSize = 22.sp)
                        }
                    }
                )
            }
        })
}