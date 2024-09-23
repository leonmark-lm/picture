package com.example.pictures.features.view.AddPicturePage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pictures.core.data.PictureRepository
import com.example.pictures.core.data.models.Picture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddPictureViewModel (savedStateHandle: SavedStateHandle, val pictureRepository: PictureRepository) : ViewModel() {
    val newPictureState = mutableStateOf(Picture(null, null, null))
    val pictureUriFromIntent = (savedStateHandle.get(AddPictureScreenNavDestination.itemUriArg) as? String)?.let{Uri.parse(it)}
    fun insert() = viewModelScope.launch(Dispatchers.IO) {
        newPictureState.value.let{
            if (validateInput(it)){
                pictureRepository.insertPicture(it)
            }
        }
    }

    companion object{
        val MAX_TITLE_LENGTH = 30
        val DEFAULT_TITLE_VALUE = ""
    }

    private fun validateInput(picture: Picture): Boolean {
        return with(picture) {
            title != null && image != null && title?.run{length <= MAX_TITLE_LENGTH} ?: false
        }
    }

    fun getBitmapByUri(context: Context, uri: Uri): Bitmap {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()
        return bitmap
    }
}