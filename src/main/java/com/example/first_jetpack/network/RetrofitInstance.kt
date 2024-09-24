package com.example.first_jetpack.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object RetrofitInstance {

        private const val BASE_URL = "https://imdb-top-100-movies.p.rapidapi.com"
        private const val API_KEY = "184b959722msh69771584429fb74p125779jsn9dc0568b0436"
        private const val HOST = "imdb-top-100-movies.p.rapidapi.com"

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val headersInterceptor = Interceptor { chain ->
            val original: Request = chain.request()
            val request: Request = original.newBuilder()
                .header("x-rapidapi-key", API_KEY)
                .header("x-rapidapi-host", HOST)
                .build()
            chain.proceed(request)
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headersInterceptor)
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService: ApiService = retrofit.create(ApiService::class.java)

}
