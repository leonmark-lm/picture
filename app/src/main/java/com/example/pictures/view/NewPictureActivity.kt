package com.example.pictures.view

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import com.example.pictures.model.Entities.PictureEntity
import com.example.pictures.view.Components.PictureAddScreen
import com.example.pictures.viewmodels.PicturesViewModel
import com.example.pictures.viewmodels.getImageById
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class NewPictureActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments : Bundle? = intent.extras
        var picture : PictureEntity? = null

        val viewModel : PicturesViewModel by viewModels { PicturesViewModel.factory }

        setContent{
            arguments?.let {
                val id : Int = arguments.getInt("id")
                runBlocking(Dispatchers.IO) {
                    picture = viewModel.getById(id = id).first()
                }
            }
            PictureAddScreen(picture, viewModel)
        }
    }
}