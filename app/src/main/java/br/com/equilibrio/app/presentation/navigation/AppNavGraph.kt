package br.com.equilibrio.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.equilibrio.app.presentation.home.HomeScreen
import br.com.equilibrio.app.presentation.home.HomeViewModel
import br.com.equilibrio.app.presentation.onboarding.OnboardingScreen

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
}

@Composable
fun AppNavGraph(startDestination: String = Screen.Onboarding.route) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(navController)
        }
        composable(Screen.Home.route) {
            // ViewModel provido pelo Hilt (sem factory/manual)
            val vm: HomeViewModel = hiltViewModel()
            HomeScreen(vm)
        }
    }
}
