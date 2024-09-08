package com.example.pictures.view.PictureAddEditPage.Components

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.pictures.R
import com.example.pictures.model.Entities.PictureEntity
import com.example.pictures.viewmodels.PicturesViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PictureAddEditScreen(picture: PictureEntity? = null, viewModel: PicturesViewModel){
    val context = LocalContext.current
    val activity = if (context is Activity) context else null

    val imageTitle = remember{mutableStateOf<String?>(picture?.title)}
    val image = remember { mutableStateOf<Bitmap?>(picture?.image?.asAndroidBitmap()) }

    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ){uri: Uri? ->
        imageUri.value = uri
    }

    imageUri.value?.let {
        image.value = getBitmapByUri(context, it)
    }

    Column(modifier = Modifier.fillMaxSize().padding(5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Card (modifier = Modifier.size(300.dp, 500.dp),
            shape = RoundedCornerShape(15.dp)){
            image.value?.let{
                Image(it.asImageBitmap(),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = R.string.empty_string.toString(),
                    contentScale = ContentScale.Crop
                    )
            }
        }

        Button(onClick = {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }) {
            Text(text = R.string.load_image.toString())
        }
        TextField(value = imageTitle.value ?: R.string.empty_string.toString(),
                onValueChange = {text -> imageTitle.value = text},
                singleLine = true)
        Button(onClick = {
            with(viewModel){
                pictureEntity = picture
                newImage.value = image.value?.asImageBitmap()
                newTitle.value = imageTitle.value ?: R.string.empty_string.toString()
                insert()
            }

            activity!!.finish()
        }) {
            Text(text = R.string.save_image.toString())
        }
    }
}

fun getBitmapByUri(context: Context, uri: Uri): Bitmap?{
    val inputStream = context.contentResolver.openInputStream(uri)
    val bitmap : Bitmap? = BitmapFactory.decodeStream(inputStream)
    inputStream?.close()
    return bitmap
}