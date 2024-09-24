package com.example.first_jetpack.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.first_jetpack.dataClass.Album
import com.example.first_jetpack.viewModel.AlbumViewModel


@Composable
fun MovieDetailScreen(albumId: String?, viewModel: AlbumViewModel) {
    val context = LocalContext.current
    val album = remember { mutableStateOf<Album?>(null) }

    LaunchedEffect(albumId) {
        if (albumId != null) {
            album.value = viewModel.fetchAlbumById(albumId)
        } else {
            Toast.makeText(context, "Invalid album ID", Toast.LENGTH_SHORT).show()
        }
    }

    album.value?.let { album ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(album.big_image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Title: ${album.title}", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Year: ${album.year}", style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Rating: ${album.rating}", style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Description:", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = album.description, style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                album.imdb_link?.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(url)
                    }
                    context.startActivity(intent)
                } ?: Toast.makeText(context, "No trailer link available", Toast.LENGTH_SHORT).show()
            }) {
                Text("Watch Trailer")
            }
        }
    }
}
