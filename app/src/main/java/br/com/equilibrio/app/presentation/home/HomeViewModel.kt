package br.com.equilibrio.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.equilibrio.app.data.local.entity.AppUsageEntity
import br.com.equilibrio.app.data.repository.AppUsageRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel (private val repository: AppUsageRepository) : ViewModel() {

    val allUsage: StateFlow<List<AppUsageEntity>> =
        repository.getAllUsage()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addUsage(usage: AppUsageEntity) {
        viewModelScope.launch {
            repository.insertUsage(usage)
        }
    }
}

class HomeViewModelFactory (private val repository: AppUsageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}