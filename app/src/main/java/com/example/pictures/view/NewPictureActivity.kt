package com.example.pictures.view

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import com.example.pictures.model.Entities.PictureEntity
import com.example.pictures.view.Components.PictureAddScreen
import com.example.pictures.viewmodel.getImageById

class NewPictureActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments : Bundle? = intent.extras
        var picture : PictureEntity? = null

        setContent{
            arguments?.let {
                val id : Int = arguments.getInt("id")
                picture = getImageById(id = id)
            }
            PictureAddScreen(picture)
        }
    }
}