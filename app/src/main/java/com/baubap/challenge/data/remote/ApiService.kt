package com.baubap.challenge.data.remote

import com.baubap.challenge.data.dto.LoginRequest
import com.baubap.challenge.data.dto.LoginResponse
import com.baubap.challenge.data.dto.RegisterRequest
import com.baubap.challenge.data.dto.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

}