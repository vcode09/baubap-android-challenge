package com.baubap.challenge.data.remote

import com.baubap.challenge.data.dto.LoginRequest
import com.baubap.challenge.data.dto.LoginResponse
import com.baubap.challenge.data.dto.RegisterRequest
import com.baubap.challenge.data.dto.RegisterResponse
import retrofit2.Response
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val api: ApiService
) {
    suspend fun login(email: String, password: String): Response<LoginResponse> =
        api.login(LoginRequest(email, password))

    suspend fun register(email: String, password: String): Response<RegisterResponse> =
        api.register(RegisterRequest(email, password))
}