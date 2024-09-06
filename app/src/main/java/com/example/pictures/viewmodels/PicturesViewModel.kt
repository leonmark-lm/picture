package com.example.pictures.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pictures.App
import com.example.pictures.model.Entities.PictureEntity
import com.example.pictures.model.MainDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PicturesViewModel(val database: MainDb) : ViewModel(){

    val items = database.pictureEntityDAO.getAll()
    val newTitle = mutableStateOf<String?>(null)
    val newImage = mutableStateOf<ImageBitmap?>(null)
    var pictureEntity: PictureEntity? = null

    fun insert() = viewModelScope.launch {
        if (newImage.value != null && newTitle.value != null) {
            val item = pictureEntity?.copy(title = newTitle.value!!, image = newImage.value!!)
                ?: PictureEntity(title = newTitle.value!!, image = newImage.value!!)
            database.pictureEntityDAO.insert(item)
            pictureEntity = null
            newTitle.value = null
        }
    }

    fun delete(entity: PictureEntity) = viewModelScope.launch {
        database.pictureEntityDAO.delete(entity)
    }

    fun getById(id : Int) : Flow<PictureEntity?> {
        return database.pictureEntityDAO.getById(id)
    }

    companion object{
        val factory = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val database = (checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as App).database
                return PicturesViewModel(database) as T
            }
        }
    }
}