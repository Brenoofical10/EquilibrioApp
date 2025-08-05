package br.com.equilibrio.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import br.com.equilibrio.app.presentation.AppNavGraph
import br.com.equilibrio.app.util.OnboardingDataStore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        setContent {
            val completed by OnboardingDataStore.isOnboardingCompleted(context)
                .collectAsState(initial = false)
            AppNavGraph(
                startDestination = if (completed) "home" else "onboarding"
            )
        }
    }
}