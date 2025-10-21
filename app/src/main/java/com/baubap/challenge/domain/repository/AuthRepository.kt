package com.baubap.challenge.domain.repository

import com.baubap.challenge.domain.model.UserD

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): Result<UserD>

    suspend fun register(
        email: String,
        password: String
    ): Result<UserD>
}