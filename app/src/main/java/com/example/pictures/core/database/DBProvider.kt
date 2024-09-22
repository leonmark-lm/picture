package com.example.pictures.core.database

import android.content.Context
import androidx.room.Room

object DBProvider {
    private lateinit var appContext: Context
    private lateinit var dbName: String
    val database by lazy { buildMainDB() }

    fun init(context: Context, dbName : String){
        appContext = context
        this.dbName = dbName
    }

    private fun buildMainDB() : MainDb {
        return Room.databaseBuilder(
            appContext,
            MainDb::class.java,
            dbName)
            .build()
    }
}