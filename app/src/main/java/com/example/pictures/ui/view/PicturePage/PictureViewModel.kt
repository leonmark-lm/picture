package com.example.pictures.ui.view.PicturePage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pictures.core.data.PictureRepository
import com.example.pictures.core.data.models.Picture
import com.example.pictures.ui.view.EditPicturePage.EditPictureScreenNavDestination
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
                                    .map{PictureUIState(it)}
                                    .stateIn(
                                        viewModelScope,
                                        SharingStarted.Lazily,
                                        PictureUIState(Picture(null, null, null))
                                    )

    fun deletePicture() = viewModelScope.launch{
        pictureRepository.deletePicture(pictureUIState.value.picture)
    }
}

data class PictureUIState(val picture: Picture)