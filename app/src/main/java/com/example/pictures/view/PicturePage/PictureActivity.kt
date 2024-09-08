package com.example.pictures.view.PicturePage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.res.stringResource
import com.example.pictures.R
import com.example.pictures.model.Entities.PictureEntity
import com.example.pictures.view.PicturePage.Components.PictureScreen
import com.example.pictures.view.PicturePage.Components.PictureTopBar
import com.example.pictures.viewmodels.PicturesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class PictureActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel : PicturesViewModel by viewModels { PicturesViewModel.factory }
        setContent{
            val arguments : Bundle? = intent.extras
            val id = arguments!!.run{getInt(stringResource(R.string.intent_picture_id_identifier)) }
            var picture : PictureEntity? = null
            runBlocking(Dispatchers.IO) {
                picture = viewModel.getById(id = id).first()
            }

            Column{
                PictureTopBar(picture!!, viewModel)
                PictureScreen(id, viewModel)
            }
        }
    }
}