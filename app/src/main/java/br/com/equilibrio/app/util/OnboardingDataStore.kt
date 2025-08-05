package br.com.equilibrio.app.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("onboarding_prefs")

object OnboardingDataStore {
    private val ONBOARDING_KEY = booleanPreferencesKey("onboarding_completed")

    //Function for saved onboarding if completed
    suspend fun setOnboardingCompleted(context: Context, completed: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[ONBOARDING_KEY] = completed
        }
    }

    //Function for validation of onboarding completed
    fun isOnboardingCompleted(context: Context): Flow<Boolean> =
        context.dataStore.data.map { prefs ->
            prefs[ONBOARDING_KEY] ?: false
    }
}


