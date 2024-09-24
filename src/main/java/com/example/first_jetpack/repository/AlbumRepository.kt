package com.example.first_jetpack.repository

import com.example.first_jetpack.dataClass.Album
import com.example.first_jetpack.network.RetrofitInstance

class AlbumRepository {
    private val apiService = RetrofitInstance.apiService

    suspend fun getAlbums(): List<Album> {
        return apiService.getAlbums()
    }

    suspend fun getAlbumById(id: String): Album? {
        val response = apiService.getAlbumById(id)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Failed to fetch album")
        }
    }
}
