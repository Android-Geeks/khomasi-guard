package com.company.khomasiguard.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.company.khomasiguard.data.repository.PreferenceKeys.EMAIL
import com.company.khomasiguard.data.repository.PreferenceKeys.FIRST_NAME
import com.company.khomasiguard.data.repository.PreferenceKeys.GUARD_ID
import com.company.khomasiguard.data.repository.PreferenceKeys.IS_LOGIN
import com.company.khomasiguard.data.repository.PreferenceKeys.LAST_NAME
import com.company.khomasiguard.data.repository.PreferenceKeys.OWNER_ID
import com.company.khomasiguard.data.repository.PreferenceKeys.PHONE_NUMBER
import com.company.khomasiguard.data.repository.PreferenceKeys.TOKEN
import com.company.khomasiguard.domain.model.LocalGuard
import com.company.khomasiguard.domain.repository.LocalGuardRepository
import com.company.khomasiguard.navigation.Screens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalGuardRepositoryImpl(
    private val context: Context
) : LocalGuardRepository {
    override fun getLocalGuard(): Flow<LocalGuard> {
        return context.dataStore.data.map { preferences ->
            LocalGuard(
                guardID = preferences[GUARD_ID] ?: "",
                firstName = preferences[FIRST_NAME] ?: "",
                lastName = preferences[LAST_NAME] ?: "",
                email = preferences[EMAIL] ?: "",
                phoneNumber = preferences[PHONE_NUMBER] ?: "",
                ownerId = preferences[OWNER_ID] ?: "",
                token = preferences[TOKEN] ?: ""
            )
        }
    }

    override suspend fun saveLocalGuard(localUser: LocalGuard) {
        context.dataStore.edit { settings ->
            settings[GUARD_ID] = localUser.guardID ?: ""
            settings[FIRST_NAME] = localUser.firstName ?: ""
            settings[LAST_NAME] = localUser.lastName ?: ""
            settings[EMAIL] = localUser.email ?: ""
            settings[PHONE_NUMBER] = localUser.phoneNumber ?: ""
            settings[OWNER_ID] = localUser.ownerId ?: ""
            settings[TOKEN] = localUser.token ?: ""
        }
    }
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
    val GUARD_ID = stringPreferencesKey("guard_id")
    val FIRST_NAME = stringPreferencesKey("first_name")
    val LAST_NAME = stringPreferencesKey("last_name")
    val EMAIL = stringPreferencesKey("email")
    val PHONE_NUMBER = stringPreferencesKey("phone_number")
    val OWNER_ID = stringPreferencesKey("owner_id")
    val TOKEN = stringPreferencesKey("token")
}