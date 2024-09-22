package com.example.pictures.ui.view.EditPicturePage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pictures.core.data.PictureRepository
import com.example.pictures.core.data.models.Picture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EditPictureViewModel(savedStateHandle: SavedStateHandle,
                           val pictureRepository: PictureRepository) : ViewModel() {

    private val pictureId: Int = checkNotNull(savedStateHandle[EditPictureScreenNavDestination.itemIdArg])

    val editedPictureState = mutableStateOf(Picture(pictureId, null, null))

    val pictureUIState = pictureRepository.getPictureStreamById(pictureId)
        .filterNotNull()
        .map{ EditPictureUiState(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, EditPictureUiState(editedPictureState.value))


    fun update() = viewModelScope.launch(Dispatchers.IO) {
        editedPictureState.value.let {
            if(validateInput(it)){
                pictureRepository.updatePicture(it)
            }
        }
    }

    private val MAX_TITLE_LENGTH = 30

    private fun validateInput(picture: Picture): Boolean {
        return with(picture) {
            id != null && title != null && image != null && title?.run{length <= MAX_TITLE_LENGTH} ?: false
        }
    }

}

data class EditPictureUiState(val picture: Picture)