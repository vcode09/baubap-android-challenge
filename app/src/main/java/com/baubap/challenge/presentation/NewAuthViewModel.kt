package com.baubap.challenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baubap.challenge.domain.usecase.LoginUseCase
import com.baubap.challenge.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewAuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    sealed class Event {
        object NavigateToHome : Event()
        object NavigateToLogin : Event()
        data class ShowMessage(val message: String) : Event()
    }

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    private val _events = MutableSharedFlow<Event>()
    val events: SharedFlow<Event> = _events.asSharedFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            val result = loginUseCase(email, password)
            result.fold(
                onSuccess = { userDomain ->
                    val userUi = userDomain.toUI()
                    _state.update { it.copy(isLoading = false, user = userUi) }
                    _events.emit(Event.NavigateToHome)
                },
                onFailure = { e ->
                    _state.update { it.copy(isLoading = false, errorMessage = e.message) }
                    _events.emit(Event.ShowMessage(e.message ?: "Error"))
                }
            )
        }
    }
    fun register(email: String, password: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            val result = registerUseCase(email, password)
            result.fold(
                onSuccess = { userDomain ->
                    val userUi = userDomain.toUI()
                    _state.update { it.copy(isLoading = false, user = userUi) }
                    _events.emit(Event.NavigateToHome)
                },
                onFailure = { e ->
                    _state.update { it.copy(isLoading = false, errorMessage = e.message) }
                    _events.emit(Event.ShowMessage(e.message ?: "Error"))
                }
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            _state.update { it.copy(user = null, errorMessage = null, isLoading = false) }
            _state.update { UiState() }
            _events.emit(Event.NavigateToLogin)
        }
        clearError()
    }

    fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }

}

