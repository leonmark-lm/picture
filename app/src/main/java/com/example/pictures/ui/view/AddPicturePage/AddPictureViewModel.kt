package com.example.pictures.ui.view.AddPicturePage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pictures.core.data.PictureRepository
import com.example.pictures.core.data.models.Picture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddPictureViewModel (val pictureRepository: PictureRepository) : ViewModel() {
    val newPictureState = mutableStateOf(Picture(null, null, null))

    fun insert() = viewModelScope.launch(Dispatchers.IO) {
        newPictureState.value.let{
            if (validateInput(it)){
                pictureRepository.insertPicture(it)
            }
        }
    }

    private val MAX_TITLE_LENGTH = 30

    private fun validateInput(picture: Picture): Boolean {
        return with(picture) {
            title != null && image != null && title?.run{length <= MAX_TITLE_LENGTH} ?: false
        }
    }
}