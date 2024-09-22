import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pictures.core.data.RepositoryProvider
import com.example.pictures.ui.view.HomePage.MainScreenViewModel
import com.example.pictures.ui.view.AddPicturePage.AddPictureViewModel
import com.example.pictures.ui.view.EditPicturePage.EditPictureViewModel
import com.example.pictures.ui.view.PicturePage.PictureViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            MainScreenViewModel(RepositoryProvider.dbPictureRepository)
        }
        initializer {
            AddPictureViewModel(RepositoryProvider.dbPictureRepository)
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