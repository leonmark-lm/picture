package com.example.pictures.view.MainPage

import android.database.CursorWindow
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import com.example.pictures.view.MainPage.Components.MainTopBar
import com.example.pictures.view.MainPage.Components.PicturesGridScreen
import com.example.pictures.viewmodels.PicturesViewModel
import java.lang.reflect.Field


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            val field: Field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.setAccessible(true)
            field.set(null, 100 * 1024 * 1024) //the 100MB is the new size
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val viewModel : PicturesViewModel by viewModels { PicturesViewModel.factory }
        setContent {

            Column{
                MainTopBar()
                PicturesGridScreen(viewModel)
            }

        }
    }
  
}