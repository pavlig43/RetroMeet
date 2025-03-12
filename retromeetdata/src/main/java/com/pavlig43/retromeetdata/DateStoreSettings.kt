package com.pavlig43.retromeetdata

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DateStoreSettings(context: Context) {

    private val dataStore = context.dataStore

    val userId: Flow<Int> = dataStore.data
        .map { preference ->
            preference[USER_ID] ?: 0
        }

    suspend fun setUserId(userId: Int) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    companion object {
        val USER_ID = intPreferencesKey("user_id")
    }
}
