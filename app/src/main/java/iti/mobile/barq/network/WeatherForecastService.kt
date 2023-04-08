package iti.mobile.barq.network

import iti.mobile.barq.model.Constants
import iti.mobile.barq.model.WeatherForecast
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastService {
    @GET("onecall?")
    suspend fun getCurrentWeather(@Query("lat") latitude: String,
                                  @Query("lon") longitude: String,
                                  @Query("appid") appId:String= Constants.APP_ID,
                                  @Query("lang") language:String,
                                  @Query("units") unitOfMeasurement:String
    ): WeatherForecast
}