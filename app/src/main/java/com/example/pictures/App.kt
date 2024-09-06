package com.example.pictures

import android.app.Application
import androidx.activity.viewModels
import com.example.pictures.model.MainDb
import com.example.pictures.viewmodels.PicturesViewModel

class App : Application() {

    val database by lazy { MainDb.getInstance(this)}

    override fun onCreate() {
        super.onCreate()
    }
}