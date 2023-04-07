package iti.mobile.barq.network
import iti.mobile.barq.model.WeatherForecast

class RemoteSource private constructor():IRemoteSource {
    private val weatherForecastService:WeatherForecastService by lazy {
        RetrofitHelper.getInstance().create(WeatherForecastService::class.java)
    }
    override suspend fun getCurrentWeather(
        latitude: String,
        longitude: String,
        language: String,
        unitOfMeasurement: String
    ): WeatherForecast {
        return weatherForecastService.getCurrentWeather(
            latitude=latitude,
            longitude=longitude,
            language=language,
            unitOfMeasurement=unitOfMeasurement
        )
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