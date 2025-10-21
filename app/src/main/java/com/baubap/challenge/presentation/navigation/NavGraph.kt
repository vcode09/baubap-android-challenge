package com.baubap.challenge.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.baubap.challenge.AuthViewModel
import com.baubap.challenge.HomeScreen
import com.baubap.challenge.LoginScreen
import com.baubap.challenge.RegisterScreen


private const val DURATION = 280

object Routes {
    const val Login = "login"
    const val Register = "register"
    const val Home = "home"
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthApp(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel()
) {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = Routes.Login,
        modifier = modifier
    ) {
        // LOGIN
        composable(
            route = Routes.Login,
            // primer screen: sin animación de entrada
            enterTransition = { fadeIn(animationSpec = tween(0)) },
            exitTransition = {
                when (targetState.destination.route) {
                    Routes.Register ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(DURATION)
                        ) + fadeOut(tween(DURATION))

                    Routes.Home ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(DURATION)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                // volver desde Register → Login
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(DURATION)
                ) + fadeIn(tween(DURATION))
            },
            popExitTransition = { fadeOut(tween(120)) }
        ) {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = { nav.navigate(Routes.Register) },
                onNavigateToHome = {
                    nav.navigate(Routes.Home) {
                        popUpTo(Routes.Login) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // REGISTER
        composable(
            route = Routes.Register,
            enterTransition = {
                // push desde Login → Register
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(DURATION)
                ) + fadeIn(tween(DURATION))
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Routes.Login ->
                        // back a Login
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(DURATION)
                        ) + fadeOut(tween(DURATION))

                    Routes.Home ->
                        // push a Home
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(DURATION)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(DURATION)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(DURATION)
                ) + fadeOut(tween(DURATION))
            }
        ) {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateToLogin = { nav.popBackStack() },
                onNavigateToHome = {
                    nav.navigate(Routes.Home) {
                        popUpTo(Routes.Login) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // HOME
        composable(
            route = Routes.Home,
            enterTransition = {
                // desde Login o Register → Home
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(DURATION)
                ) + fadeIn(tween(DURATION))
            },
            exitTransition = { fadeOut(tween(160)) },
            popEnterTransition = { fadeIn(tween(160)) },
            popExitTransition = {
                // logout → Login
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(DURATION)
                ) + fadeOut(tween(DURATION))
            }
        ) {
            HomeScreen(
                viewModel = authViewModel,
                onLogout = {
                    nav.navigate(Routes.Login) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
