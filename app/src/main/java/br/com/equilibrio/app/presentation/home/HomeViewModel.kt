package br.com.equilibrio.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.equilibrio.app.data.local.entity.AppUsageEntity
import br.com.equilibrio.app.data.repository.AppUsageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AppUsageRepository
) : ViewModel() {

    private fun today(): String = LocalDate.now().toString()
    private fun sevenDaysAgo(): String = LocalDate.now().minusDays(6).toString()

    val allUsage: StateFlow<List<AppUsageEntity>> =
        repository.getAllUsage()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val todayTotals: StateFlow<Map<String, Int>> =
        repository.sumByCategoryForDate(today())
            .map { list -> list.associate { it.category to it.total } }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyMap())

    val last7Days = repository
        .sumByCategoryLastDays(sevenDaysAgo())
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addUsage(usage: AppUsageEntity) = viewModelScope.launch { repository.insertUsage(usage) }
    fun clearAll() = viewModelScope.launch { repository.clearAll() }

    fun addSampleToday() {
        val appsLazer = listOf("YouTube", "Instagram", "TikTok")
        val appsProd  = listOf("Kindle", "Biblia", "Notion")
        val today = today()
        val samples = buildList {
            repeat(3) {
                add(AppUsageEntity(appName = appsLazer.random(), category = "lazer", durantion = (5..50).random(), date = today))
                add(AppUsageEntity(appName = appsProd.random(),  category = "produtivo", durantion = (5..50).random(), date = today))
            }
        }
        viewModelScope.launch { samples.forEach { repository.insertUsage(it) } }
    }
}
