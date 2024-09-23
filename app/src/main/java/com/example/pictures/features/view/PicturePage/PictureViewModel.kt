package com.example.pictures.features.view.PicturePage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pictures.core.data.PictureRepository
import com.example.pictures.core.data.models.Picture
import com.example.pictures.features.view.EditPicturePage.EditPictureScreenNavDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PictureViewModel(savedStateHandle: SavedStateHandle,
                        val pictureRepository: PictureRepository) : ViewModel() {

    private val pictureId: Int = checkNotNull(savedStateHandle[EditPictureScreenNavDestination.itemIdArg])

    val pictureUIState : StateFlow<PictureUIState> = pictureRepository.getPictureStreamById(pictureId)
                                    .filterNotNull()
                                    .map{ PictureUIState(it) }
                                    .stateIn(
                                        viewModelScope,
                                        SharingStarted.Lazily,
                                        PictureUIState(Picture(null, null, null))
                                    )

    fun deletePicture() = viewModelScope.launch{
        pictureRepository.deletePicture(pictureUIState.value.picture)
    }

    companion object{
        val MEDIA_STORE_PICTURE_TITLE = "Image"
    }

    fun sharePicture(context: Context, image: Bitmap){
        val INTENT_TYPE = "image/*"
        val INTENT_TITLE = "Share to:"

        val activity = context as Activity
        val path = MediaStore.Images.Media.insertImage(activity.contentResolver, image, MEDIA_STORE_PICTURE_TITLE, null)
        val uri = Uri.parse(path)
        Intent().apply{
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = INTENT_TYPE
        }.also {
            context.startActivity(Intent.createChooser(it,INTENT_TITLE))
        }
    }
}

data class PictureUIState(val picture: Picture)