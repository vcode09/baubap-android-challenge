package com.baubap.challenge.presentation

import com.baubap.challenge.domain.model.UserD

data class UiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null
)
