package com.example.pictures.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import com.example.pictures.view.Components.MainTopBar
import com.example.pictures.view.Components.PicturesGridScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column{
                MainTopBar()
                PicturesGridScreen()
            }

        }
    }
  
}