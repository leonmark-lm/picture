package com.example.pictures.ui.view

import android.database.CursorWindow
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.pictures.ui.navigation.PictureNavGraph
import java.lang.reflect.Field


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCursorWindowSize(100 * 1024 * 1024)
        setContent {
            PictureNavGraph(navController = rememberNavController())
        }
    }

    fun setCursorWindowSize(size: Int){
        try {
            CursorWindow::class.java.getDeclaredField("sCursorWindowSize").run{
                setAccessible(true)
                set(null, size)
            }
        } catch (e: Exception) {
            Log.d("CursorWindow", "$e")
        }
    }
}