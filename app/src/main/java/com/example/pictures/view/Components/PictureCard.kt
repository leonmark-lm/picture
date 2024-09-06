package com.example.pictures.view.Components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pictures.model.Entities.PictureEntity
import com.example.pictures.view.PictureActivity

@Composable
fun PictureCard(
    picture: PictureEntity
){
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .height(200.dp)
            .clickable {
                val intent = Intent(context, PictureActivity::class.java)
                intent.putExtra("id", picture.id)
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(15.dp),

        ) {
        Box() {
            Image(modifier = Modifier.fillMaxSize(),
                bitmap = picture.image,
                contentDescription = "",
                contentScale = ContentScale.Crop)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 30f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(picture.title, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }
    }


}