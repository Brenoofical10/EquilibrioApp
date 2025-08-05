package br.com.equilibrio.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.equilibrio.app.data.local.entity.AppUsageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppUsageDao {
    @Insert
    suspend fun insertUsage(usage: AppUsageEntity)

    @Query("SELECT * FROM app_usage ORDER BY date DESC")
    fun getAllUsage(): Flow<List<AppUsageEntity>>

    @Query("SELECT * FROM app_usage WHERE category = :category ORDER BY date DESC")
    fun getUsageByCategory(category: String): Flow<List<AppUsageEntity>>

    @Query("DELETE FROM app_usage")
    suspend fun clearAll()
}
