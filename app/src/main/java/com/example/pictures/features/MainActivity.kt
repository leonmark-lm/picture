package com.example.pictures.features

import android.database.CursorWindow
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pictures.features.view.PictureApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCursorWindowSize(100 * 1024 * 1024)
        setContent {
            PictureApp(intent = intent)
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