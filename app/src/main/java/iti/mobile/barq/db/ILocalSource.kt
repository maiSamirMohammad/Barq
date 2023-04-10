package iti.mobile.barq.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import iti.mobile.barq.model.Alert
import iti.mobile.barq.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface ILocalSource {

    suspend fun insertCurrentWeather(weatherForecast: WeatherForecast): Long
    suspend fun getStoredWeatherForecast(): Flow<WeatherForecast>?


    //alerts
    suspend fun getAlertByCompositeKey(lat: Double, lon: Double, startDate: Long): Flow<Alert>


    suspend fun getAlerts(): Flow<List<Alert>>


    suspend fun insertAlert(alert: Alert): Long


    suspend fun deleteAlert(alert: Alert)
}