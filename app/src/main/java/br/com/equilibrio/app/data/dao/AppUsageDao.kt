package br.com.equilibrio.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.equilibrio.app.data.local.entity.AppUsageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppUsageDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsage(usage: AppUsageEntity)

    @Query("SELECT * FROM app_usage ORDER BY date DESC")
    fun getAllUsage(): Flow<List<AppUsageEntity>>

    @Query("SELECT * FROM app_usage WHERE category = :category ORDER BY date DESC")
    fun getUsageByCategory(category: String): Flow<List<AppUsageEntity>>

    @Query("DELETE FROM app_usage")
    suspend fun clearAll()

    @Query("""
        SELECT category AS category, SUM(durantion) AS total
        FROM app_usage
        WHERE date = :date
        GROUP BY category
    """)

    fun sumByCategoryForDate(date: String): Flow<List<CategoryTotal>>

    @Query("""
        SELECT date AS date, category AS category, SUM(durantion) AS total
        FROM app_usage
        WHERE date >= :fromDate
        GROUP BY date, category
        ORDER BY date ASC
        """)
    fun sumByCategoryLastDays(fromDate: String): Flow<List<DailyCategoryTotal>>

    data class CategoryTotal(
        val category: String,
        val total: Int
    )

    data class DailyCategoryTotal(
        val date: String,
        val category: String,
        val total: Int
    )
}
