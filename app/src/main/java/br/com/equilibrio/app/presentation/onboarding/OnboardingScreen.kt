package br.com.equilibrio.app.presentation.onboarding

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.equilibrio.app.R
import br.com.equilibrio.app.util.OnboardingDataStore
import kotlinx.coroutines.launch

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int
)

val onboardingPages = listOf(
    OnboardingPage(
        title = "Bem-vindo!",
        description = "Descubra como equilibrar lazer e produtividade.",
        imageRes = R.drawable.ic_launcher_foreground
    ),
    OnboardingPage(
        title = "Permissões",
        description = "Permita acesso ao uso dos apps para estatísticas personalizadas.",
        imageRes = R.drawable.ic_launcher_foreground // Troque pelo drawable correto
    ),
    OnboardingPage(
        title = "Personalize",
        description = "Escolha seus apps produtivos e de lazer favoritos.",
        imageRes = R.drawable.ic_launcher_foreground
    )
)

@Composable
fun OnboardingPageView(page: OnboardingPage) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Image(
            painter = painterResource(id = page.imageRes),
            contentDescription = page.title,
            modifier = Modifier.size(180.dp)
        )
        Spacer(Modifier.height(32.dp))
        Text(page.title, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        Text(page.description, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun OnboardingScreen(navController: NavController) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f, pageCount = { onboardingPages.size })
    val scope = rememberCoroutineScope()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingPageView(page = onboardingPages[page])
            }

            Spacer(Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(onboardingPages.size) { index ->
                    val color = if (pagerState.currentPage == index)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                    Box(
                        Modifier
                            .padding(4.dp)
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }
            Button(
                onClick = {
                    if (pagerState.currentPage == onboardingPages.lastIndex) {

                        // Salve no DataStore aqui, se quiser
                        scope.launch {
                            OnboardingDataStore.setOnboardingCompleted(context, true)
                        navController.navigate("home") {
                            popUpTo("onboarding") { inclusive = true }
                        }
                      }
                    } else {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    }
                },
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(0.7f)
            ) {
                Text(
                    if (pagerState.currentPage == onboardingPages.lastIndex) "Começar" else "Próximo"
                )
            }
        }
    }
}
