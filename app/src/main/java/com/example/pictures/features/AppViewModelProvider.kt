package com.example.pictures.features

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pictures.core.data.RepositoryProvider
import com.example.pictures.features.view.HomePage.MainScreenViewModel
import com.example.pictures.features.view.AddPicturePage.AddPictureViewModel
import com.example.pictures.features.view.EditPicturePage.EditPictureViewModel
import com.example.pictures.features.view.PicturePage.PictureViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            MainScreenViewModel(RepositoryProvider.dbPictureRepository)
        }
        initializer {
            AddPictureViewModel(this.createSavedStateHandle(),
                RepositoryProvider.dbPictureRepository)
        }
        initializer {
            EditPictureViewModel(this.createSavedStateHandle(),
                RepositoryProvider.dbPictureRepository)
        }
        initializer {
            PictureViewModel(this.createSavedStateHandle(),
                RepositoryProvider.dbPictureRepository)
        }
    }
}