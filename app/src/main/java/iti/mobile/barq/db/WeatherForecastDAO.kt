package iti.mobile.barq.db

import androidx.room.*
import iti.mobile.barq.model.Alert
import iti.mobile.barq.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherForecastDAO {
    @Query("SELECT * FROM weather_forecast LIMIT 1")
    fun getStoredWeatherForecast(): Flow<WeatherForecast>?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(weatherForecast: WeatherForecast): Long
}
@Dao
interface AlertDao {
    @Query("SELECT * FROM Alert WHERE lat = :lat AND lon = :lon AND startDate = :startDate")
    fun getAlertByCompositeKey(lat: Double, lon: Double, startDate: Long): Flow<Alert>

    @Query("SELECT * From Alert")
    fun getAlerts(): Flow<List<Alert>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlert(alert: Alert): Long

    @Delete
    suspend fun deleteAlert(alert: Alert)
}