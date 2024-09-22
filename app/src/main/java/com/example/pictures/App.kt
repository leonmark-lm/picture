package com.example.pictures

import android.app.Application
import com.example.pictures.core.database.DBProvider
import com.example.pictures.core.database.MainDb

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DBProvider.init(this, "pictures_db_2")
    }
}