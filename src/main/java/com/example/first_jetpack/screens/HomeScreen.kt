package com.example.first_jetpack.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.first_jetpack.AuthState
import com.example.first_jetpack.AuthViewMode
import com.example.first_jetpack.dataClass.Album
import com.example.first_jetpack.navigation.Routes
import com.example.first_jetpack.viewModel.AlbumViewModel
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navController: NavHostController, authViewMode: AuthViewMode, viewModel: AlbumViewModel) {

    val context = LocalContext.current
    val authState = authViewMode.authState.observeAsState()
    val albumResponse = viewModel.albums.observeAsState(emptyList())
    val albums = albumResponse.value
    val scrollState = rememberLazyListState()


    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> viewModel.fetchAlbums()
            is AuthState.Unauthenticated -> navController.navigate(Routes.login)
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
        while (true) {
            delay(2000) // Delay in milliseconds before scrolling to the next item
            val nextIndex = (scrollState.firstVisibleItemIndex + 1)
            scrollState.animateScrollToItem(nextIndex)
        }

    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        LazyRow(
            state = scrollState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(albums) { item ->
                CardItemView(item, navController)
            }
        }

        Text(text = "Favourites", modifier = Modifier.align(Alignment.Start).padding(8.dp))

        LazyColumn (
            modifier = Modifier.padding(16.dp)
                ){
            items(albums) { album ->
                AlbumView(album = album, navController)
            }
        }

    }
}



@Composable
fun AlbumView(album: Album, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("movieDetail/${album.id}")
            },
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Movie Thumbnail
            Image(
                painter = rememberAsyncImagePainter(model = album.thumbnail),
                contentDescription = "Movie Thumbnail",
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
            )

            // Movie Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = album.title,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = album.description,
                    style = MaterialTheme.typography.body2,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Rating: ${album.rating}",
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = "${album.year}",
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}

@Composable
fun CardItemView(album: Album, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(150.dp, 200.dp)
            .clickable {
                navController.navigate("movieDetail/${album.id}")
            },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(album.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentScale = ContentScale.Crop
            )

        }
    }
}