package br.com.equilibrio.app.presentation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.equilibrio.app.data.local.AppDatabase
import br.com.equilibrio.app.data.repository.AppUsageRepository
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

    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val repository = remember { AppUsageRepository(db.appUsageDao()) }
    val homeViewModel = remember { HomeViewModel(repository) }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Onboarding.route) { OnboardingScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(homeViewModel) }
    }
}
