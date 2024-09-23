package com.example.pictures.features.view

import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pictures.features.navigation.PictureNavGraph
import com.example.pictures.features.view.AddPicturePage.AddPictureScreenNavDestination
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun PictureApp(intent: Intent){
    val navController = rememberNavController()
    when {
        intent.action == Intent.ACTION_SEND ->{
            intent.type?.apply {
                if (startsWith("image/")){
                    PictureNavGraph(navController = navController)
                    handleSentImage(intent, navController)
                }
            }
        }
        else ->{
            PictureNavGraph(navController = navController)
        }
    }
}

private fun handleSentImage(intent: Intent, navController: NavController) {
    (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
        val encodedUri = URLEncoder.encode(it.toString(), StandardCharsets.UTF_8.toString())
        navController.navigate("${AddPictureScreenNavDestination.route}/$encodedUri")
    }
}