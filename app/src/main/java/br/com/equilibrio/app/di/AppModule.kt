package br.com.equilibrio.app.di

import android.content.Context
import androidx.room.Room
import br.com.equilibrio.app.data.local.AppDatabase
import br.com.equilibrio.app.data.local.dao.AppUsageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_usage_db").build()

    @Provides
    fun provideAppUsageDao(db: AppDatabase): AppUsageDao = db.appUsageDao()
}
