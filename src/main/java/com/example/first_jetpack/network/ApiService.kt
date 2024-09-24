package com.example.first_jetpack.network

import com.example.first_jetpack.dataClass.Album
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("/")
    suspend fun getAlbums(): List<Album>

    @GET("/{id}")
    suspend fun getAlbumById(@Path("id") id: String): Response<Album>
}
