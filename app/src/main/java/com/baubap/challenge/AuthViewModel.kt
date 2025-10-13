package com.baubap.challenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

data class AuthState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null,
)

sealed class AuthSideEffect {
    object NavigateToHome : AuthSideEffect()
    data class ShowError(val message: String) : AuthSideEffect()
}

data class User(
    val id: Int? = null,
    val token: String,
    val email: String
)

class AuthViewModel : ViewModel(), ContainerHost<AuthState, AuthSideEffect> {

    override val container = container<AuthState, AuthSideEffect>(AuthState())

    fun login(email: String, password: String) = intent {
        reduce { state.copy(isLoading = true, errorMessage = null) }

        try {
            val request = LoginRequest(email, password)
            val response = ApiClient.apiService.login(request)

            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!
                val user = User(
                    token = loginResponse.token,
                    email = email
                )
                reduce {
                    state.copy(
                        isLoading = false,
                        user = user,
                        errorMessage = null
                    )
                }
                postSideEffect(AuthSideEffect.NavigateToHome)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    "Error de login: ${errorResponse.error}"
                } catch (e: Exception) {
                    when (response.code()) {
                        400 -> "Error de login: Datos inválidos. Verifica email y contraseña."
                        401 -> "Error de login: Credenciales incorrectas."
                        403 -> "Error de login: Acceso denegado. Verifica tu API key."
                        404 -> "Error de login: Servicio no encontrado."
                        500 -> "Error del servidor. Intenta más tarde."
                        else -> "Error de login desconocido. Código: ${response.code()}"
                    }
                }

                reduce {
                    state.copy(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }
                postSideEffect(AuthSideEffect.ShowError(errorMessage))
            }
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is java.net.UnknownHostException -> "Error de conexión: Verifica tu conexión a internet"
                is java.net.SocketTimeoutException -> "Error de conexión: Tiempo de espera agotado"
                else -> "Error de conexión: ${e.message}"
            }
            reduce {
                state.copy(
                    isLoading = false,
                    errorMessage = errorMessage
                )
            }
            postSideEffect(AuthSideEffect.ShowError(errorMessage))
        }
    }

    fun register(email: String, password: String) = intent {
        reduce { state.copy(isLoading = true, errorMessage = null) }

        try {
            val request = RegisterRequest(email, password)
            val response = ApiClient.apiService.register(request)

            if (response.isSuccessful && response.body() != null) {
                val registerResponse = response.body()!!
                val user = User(
                    id = registerResponse.id,
                    token = registerResponse.token,
                    email = email
                )
                reduce {
                    state.copy(
                        isLoading = false,
                        user = user,
                        errorMessage = null
                    )
                }
                postSideEffect(AuthSideEffect.NavigateToHome)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    "Error de registro: ${errorResponse.error}"
                } catch (e: Exception) {
                    when (response.code()) {
                        400 -> "Error de registro: Email ya registrado o datos inválidos."
                        401 -> "Error de registro: Credenciales incorrectas."
                        403 -> "Error de registro: Acceso denegado. Verifica tu API key."
                        404 -> "Error de registro: Servicio no encontrado."
                        500 -> "Error del servidor. Intenta más tarde."
                        else -> "Error de registro desconocido. Código: ${response.code()}"
                    }
                }

                reduce {
                    state.copy(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }
                postSideEffect(AuthSideEffect.ShowError(errorMessage))
            }
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is java.net.UnknownHostException -> "Error de conexión: Verifica tu conexión a internet"
                is java.net.SocketTimeoutException -> "Error de conexión: Tiempo de espera agotado"
                else -> "Error de conexión: ${e.message}"
            }
            reduce {
                state.copy(
                    isLoading = false,
                    errorMessage = errorMessage
                )
            }
            postSideEffect(AuthSideEffect.ShowError(errorMessage))
        }
    }

    fun clearError() = intent {
        reduce { state.copy(errorMessage = null) }
    }

    fun logout() = intent {
        reduce { state.copy(user = null, errorMessage = null) }
    }
}