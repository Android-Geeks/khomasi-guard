package com.company.khomasiguard.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.company.khomasiguard.data.repository.PreferenceKeys.IS_LOGIN
import com.company.khomasiguard.domain.repository.LocalGuardRepository
import com.company.khomasiguard.navigation.Screens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalGuardRepositoryImpl(
    private val context: Context
) : LocalGuardRepository {
    override suspend fun saveIsLogin(isLogin: Boolean) {
        context.dataStore.edit { settings ->
            settings[IS_LOGIN] = isLogin
        }
    }

    override fun readAppEntry(): Flow<Screens> {
        return context.dataStore.data.map { preferences ->
            val isLogin = preferences[IS_LOGIN] ?: false
            if (!isLogin) {
                Screens.AuthNavigation
            } else {
                Screens.KhomasiNavigation
            }
        }
    }
}

private val readOnlyProperty = preferencesDataStore(name = "user_preferences")

val Context.dataStore: DataStore<Preferences> by readOnlyProperty

private object PreferenceKeys {
    val IS_LOGIN = booleanPreferencesKey("is_login")
}