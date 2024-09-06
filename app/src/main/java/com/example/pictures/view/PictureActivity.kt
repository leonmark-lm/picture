package com.example.pictures.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import com.example.pictures.view.Components.PictureScreen
import com.example.pictures.view.Components.PictureTopBar
import com.example.pictures.viewmodel.getImageById

class PictureActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            val arguments : Bundle? = intent.extras
            val id : Int = arguments!!.getInt("id")
            val picture = getImageById(id = id)
            Column{
                PictureTopBar(picture!!)
                PictureScreen(picture!!)
            }
        }
    }
}