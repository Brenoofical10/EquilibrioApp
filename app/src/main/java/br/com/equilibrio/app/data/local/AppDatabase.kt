package br.com.equilibrio.app.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.equilibrio.app.data.local.dao.AppUsageDao
import br.com.equilibrio.app.data.local.entity.AppUsageEntity

@Database(entities = [AppUsageEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appUsageDao(): AppUsageDao

    companion object{
    @Volatile
    private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_usage_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}