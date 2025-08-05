package br.com.equilibrio.app.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.equilibrio.app.data.local.entity.AppUsageEntity

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val usageList by viewModel.allUsage.collectAsState()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text("Dashboard EquilibrioApp", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))
            if (usageList.isEmpty()) {
                Text("Nenhum uso registrado ainda.")
            } else {
                usageList.forEach { usage ->
                    UsageItem(usage)
                }
            }
        }
    }
}

@Composable
fun UsageItem(usage: AppUsageEntity) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            "${usage.appName} | ${usage.category} | ${usage.durantion} min | ${usage.date}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
