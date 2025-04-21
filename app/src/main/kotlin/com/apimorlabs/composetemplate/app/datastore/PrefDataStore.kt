package com.apimorlabs.composetemplate.app.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.apimorlabs.composetemplate.app.ui.theme.Theme
import com.apimorlabs.composetemplate.app.utils.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

// This is provided by Hilt for easy usage throughout the app
class PrefDataStore(
    context: Context,
    dispatcher: CoroutineDispatcher
) {
    private object PreferenceKeys {
        val themeOption = intPreferencesKey(name = "theme_option")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = Constants.PREFERENCE_NAME,
        scope = CoroutineScope(dispatcher)
    )

    private val dataStore = context.dataStore

    // Theme Settings
    // See Theme enum class inside ui/theme/theme.kt for data meaning
    suspend fun saveThemeSetting(value: Int) {
        DataStoreHelpers.writePreference(
            dataStore,
            preferenceKey = PreferenceKeys.themeOption,
            value = value
        )
    }

    val readThemeSetting: Flow<Int> = DataStoreHelpers.readPreference(
        dataStore,
        preferenceKey = PreferenceKeys.themeOption,
        defaultValue = Theme.MATERIAL_YOU.themeValue
    )
}
