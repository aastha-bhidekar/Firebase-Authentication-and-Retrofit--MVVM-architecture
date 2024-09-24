package com.example.first_jetpack.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.first_jetpack.dataClass.Album
import com.example.first_jetpack.repository.AlbumRepository
import kotlinx.coroutines.launch
class AlbumViewModel : ViewModel() {
    private val repository = AlbumRepository()

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> = _albums
    private val _selectedAlbum = MutableLiveData<Album?>()
    val selectedAlbum: LiveData<Album?> = _selectedAlbum
    var errorMessage = mutableStateOf("")

    fun fetchAlbums() {
        viewModelScope.launch {
            try {
                val albums = repository.getAlbums()
                _albums.value = albums
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown error"
                Log.e("AlbumViewModel", errorMessage.value)
            }
        }
    }

    suspend fun fetchAlbumById(albumId: String): Album? {
        return try {
            repository.getAlbumById(albumId)
        } catch (e: Exception) {
            errorMessage.value = e.message ?: "Unknown error"
            Log.e("AlbumViewModel", errorMessage.value)
            null
        }
    }

}
