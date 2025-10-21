package com.baubap.challenge.data.repository

import com.baubap.challenge.data.remote.AuthRemoteDataSource
import com.baubap.challenge.domain.model.UserD
import com.baubap.challenge.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val ds: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Result<UserD> = runCatching {
        val resp = ds.login(email, password)
        if (resp.isSuccessful) {
            val token = resp.body()?.token ?: error("Token vacío")
            UserD(id = null, email = email, token = token)
        } else {
            val msg = resp.errorBody()?.string().orEmpty().ifBlank { "HTTP ${resp.code()}" }
            error(msg)
        }
    }

    override suspend fun register(
        email: String,
        password: String
    ): Result<UserD> = runCatching {
        val resp = ds.register(email, password)
        if (resp.isSuccessful) {
            val body = resp.body()
            val token = body?.token ?: error("Token vacío")
            UserD(id = body.id, email = email, token = token)
        } else {
            val msg = resp.errorBody()?.string().orEmpty().ifBlank { "HTTP ${resp.code()}" }
            error(msg)
        }
    }
}
