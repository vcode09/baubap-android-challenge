package com.baubap.challenge.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.baubap.challenge.presentation.NewAuthViewModel

@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    viewModel: NewAuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            if (event is NewAuthViewModel.Event.NavigateToLogin) onLogout()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "¡Bienvenido!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        state.user?.let { user ->
            Card(
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Información del Usuario",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Email: ${user.email}",
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = "Token: ${user.token.take(20)}...",
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    user.id?.let { id ->
                        Text(
                            text = "ID: $id",
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                viewModel.logout()
                onLogout()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar Sesión")
        }
    }
}