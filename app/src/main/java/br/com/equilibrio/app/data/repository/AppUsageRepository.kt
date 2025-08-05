package br.com.equilibrio.app.data.repository

import br.com.equilibrio.app.data.local.dao.AppUsageDao
import br.com.equilibrio.app.data.local.entity.AppUsageEntity
import kotlinx.coroutines.flow.Flow

class AppUsageRepository(private val dao: AppUsageDao) {

    suspend fun insertUsage(usage: AppUsageEntity) = dao.insertUsage(usage)

    fun getAllUsage(): Flow<List<AppUsageEntity>> = dao.getAllUsage()

    fun getUsageByCategory(category: String): Flow<List<AppUsageEntity>> = dao.getUsageByCategory(category)

    suspend fun clearAll() = dao.clearAll()
}
