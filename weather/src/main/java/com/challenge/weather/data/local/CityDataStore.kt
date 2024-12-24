package com.challenge.weather.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.challenge.datastore.DataStorageManager
import com.challenge.weather.data.CityRepository
import com.challenge.weather.model.City
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.lang.ref.WeakReference
import javax.inject.Inject

class CityDataStore @Inject constructor(
    @ApplicationContext context: Context,
) : DataStorageManager, CityRepository {
    override val preferencesKey: String = "current_city"
    private val cityNameKey = stringPreferencesKey("city_name_key")
    private val weakContext = WeakReference(context)
    private val Context.datastore by preferencesDataStore(preferencesKey)

    override suspend fun saveCity(city: City) {
        weakContext.get()?.datastore
            ?.edit { preferences ->
                preferences.clear()
                preferences[cityNameKey] = city.name
            }
    }

    override val savedCity: Flow<City?> = weakContext.get()
        ?.datastore
        ?.data
        ?.map { preferences ->
            preferences[cityNameKey]?.let { name ->
                City(name)
            }
        } ?: flowOf(null)
}