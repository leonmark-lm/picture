package com.example.pictures.view.MainPage.Components

import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.pictures.R
import com.example.pictures.view.PictureAddEditPage.AddEditPictureActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(){
    val context = LocalContext.current
    TopAppBar(title= { Text(stringResource(R.string.app_name), fontSize = 22.sp) },
        actions={
            IconButton(onClick = {
                Intent(context, AddEditPictureActivity::class.java).also {
                    context.startActivity(it)
                }
            }) {
                Icon(Icons.Filled.AddCircle, contentDescription = stringResource(R.string.empty_string))
            }
        })
}