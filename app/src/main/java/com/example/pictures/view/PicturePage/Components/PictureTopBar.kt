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
import androidx.compose.ui.res.stringResource
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
            Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.empty_string))
        }
        },
        actions={
            IconButton(onClick = {
                Intent(context, AddEditPictureActivity::class.java).apply {
                    putExtra("id", picture.id)
                }.also {
                    context.startActivity(it)
                }
            }) {
                Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.empty_string))

            }
            IconButton(onClick = {
                isOpenDialog.value = true
            }) {
                Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.empty_string))
            }

            if (isOpenDialog.value) {
                AlertDialog(
                    onDismissRequest = { isOpenDialog.value = false},
                    title = { Text(stringResource(R.string.confirm_action)) },
                    text = { Text(stringResource(R.string.confirm_question)) },
                    confirmButton = {
                        Button({
                            isOpenDialog.value = false
                            viewModel.delete(picture)
                            activity!!.finish()
                        }) {
                            Text(stringResource(R.string.answer_yes), fontSize = 22.sp)
                        }
                    }
                )
            }
        })
}