package com.baubap.challenge.data.remote

import com.baubap.challenge.data.remote.ApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object ApiClient {

    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        encodeDefaults = true
    }

    private const val BASE_URL = "https://reqres.in/"
    private val contentType = "application/json".toMediaType()

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
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}