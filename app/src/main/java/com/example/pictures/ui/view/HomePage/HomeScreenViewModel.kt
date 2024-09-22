package com.example.pictures.ui.view.HomePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pictures.core.data.PictureRepository
import com.example.pictures.core.data.models.Picture
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainScreenViewModel(val pictureRepository: PictureRepository) : ViewModel(){

    val homeUiState = pictureRepository.getAllPicturesStream()
        .map { HomeUiState(it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class HomeUiState(val picturesList: List<Picture> = listOf())