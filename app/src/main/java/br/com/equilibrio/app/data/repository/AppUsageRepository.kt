package br.com.equilibrio.app.data.repository

import br.com.equilibrio.app.data.local.dao.AppUsageDao
import br.com.equilibrio.app.data.local.entity.AppUsageEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppUsageRepository @Inject constructor(
    private val dao: AppUsageDao
) {
    suspend fun insertUsage(usage: AppUsageEntity) = dao.insertUsage(usage)
    fun getAllUsage() = dao.getAllUsage()
    fun getUsageByCategory(category: String) = dao.getUsageByCategory(category)
    suspend fun clearAll() = dao.clearAll()

    // agregações
    fun sumByCategoryForDate(date: String) = dao.sumByCategoryForDate(date)
    fun sumByCategoryLastDays(fromDate: String) = dao.sumByCategoryLastDays(fromDate)
}
