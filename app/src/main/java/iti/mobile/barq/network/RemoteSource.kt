package iti.mobile.barq.network

import iti.mobile.barq.model.WeatherForecast

class RemoteSource private constructor():IRemoteSource {
    val weatherForecastService:WeatherForecastService by lazy {
        RetrofitHelper.getInstance().create(WeatherForecastService::class.java)
    }
    override suspend fun getCurrentWeather(
        lat: String?,
        lon: String?,
        appId: String,
        lang: String,
        units: String
    ): WeatherForecast {
        return weatherForecastService.getCurrentWeather(lat,lon,appId,lang,units)
    }



    companion object{
        private var instance: RemoteSource?=null
        fun getInstance(): RemoteSource {
            return instance?: synchronized(this){
                val temp= RemoteSource()
                instance=temp
                temp
            }

        }
    }
}