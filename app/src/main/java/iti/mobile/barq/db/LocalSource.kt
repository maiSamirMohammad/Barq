package iti.mobile.barq.db

import android.content.Context
import iti.mobile.barq.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

class LocalSource(var context: Context) : ILocalSource {

    private val dao:WeatherForecastDAO by lazy {
        val db:AppDataBase=AppDataBase.getInstance(context)
        db.weatherForecastDAO()
    }
    override suspend fun deleteCurrentWeather(weatherForecast: WeatherForecast): Int {
       return dao.deleteCurrentWeather(weatherForecast)
    }


    override suspend fun insertCurrentWeather(weatherForecast: WeatherForecast): Long {
        return dao.insertCurrentWeather(weatherForecast)
    }

    override suspend fun getCurrentWeathers(): Flow<WeatherForecast>? {
        return dao.getCurrentWeathers()
    }

}