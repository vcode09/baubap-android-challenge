package com.baubap.challenge.presentation

import com.baubap.challenge.domain.model.UserD

fun UserD.toUI() = User(
    id = id,
    email = email,
    token = token
)

fun User.toDomain() = UserD(
    id = id,
    email = email,
    token = token
)