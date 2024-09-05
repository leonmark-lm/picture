package com.example.pictures.view.Components

import android.content.Context
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.example.pictures.view.NewPictureActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(){
    val context = LocalContext.current
    TopAppBar(title= { Text("Pictures", fontSize = 22.sp) },
        actions={
            IconButton(onClick = {
                val intent = Intent(context, NewPictureActivity::class.java)
                context.startActivity(intent)
            }) {
                Icon(Icons.Filled.AddCircle, contentDescription = "Добавить картинку")
            }
        })
}