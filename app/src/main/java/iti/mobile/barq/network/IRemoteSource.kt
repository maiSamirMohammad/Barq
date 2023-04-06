package iti.mobile.barq.network

import iti.mobile.barq.model.Constants
import iti.mobile.barq.model.WeatherForecast
import retrofit2.http.Query

interface IRemoteSource {
    suspend fun getCurrentWeather(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("appid") appId: String = Constants.APP_ID,
        @Query("lang") lang: String,
        @Query("units") units: String
    ): WeatherForecast
}