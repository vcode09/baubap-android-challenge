package com.baubap.challenge

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://reqres.in/"

    val httpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val modified = original.newBuilder()
                .addHeader("x-api-key", "reqres-free-v1")
                .build()
            chain.proceed(modified)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}