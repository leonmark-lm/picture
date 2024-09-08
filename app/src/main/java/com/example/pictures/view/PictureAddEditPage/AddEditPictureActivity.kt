package com.example.pictures.view.PictureAddEditPage

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.pictures.R
import com.example.pictures.model.Entities.PictureEntity
import com.example.pictures.view.PictureAddEditPage.Components.PictureAddEditScreen
import com.example.pictures.viewmodels.PicturesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class AddEditPictureActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments : Bundle? = intent.extras
        var picture : PictureEntity? = null

        val viewModel : PicturesViewModel by viewModels { PicturesViewModel.factory }

        setContent{
            arguments?.let {
                val id : Int = arguments.getInt(R.string.intent_picture_id_identifier.toString())
                runBlocking(Dispatchers.IO) {
                    picture = viewModel.getById(id = id).first()
                }
            }
            PictureAddEditScreen(picture, viewModel)
        }
    }
}