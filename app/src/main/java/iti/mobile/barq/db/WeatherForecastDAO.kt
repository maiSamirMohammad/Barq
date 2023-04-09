package iti.mobile.barq.db

import androidx.room.*
import iti.mobile.barq.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherForecastDAO {
    @Query("SELECT * FROM weather_forecast LIMIT 1")
    fun getStoredWeatherForecast(): Flow<WeatherForecast>?



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(weatherForecast: WeatherForecast): Long



}