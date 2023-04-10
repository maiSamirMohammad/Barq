package iti.mobile.barq.db

import android.content.Context
import iti.mobile.barq.model.Alert
import iti.mobile.barq.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

class LocalSource(var context: Context) : ILocalSource {

    private val dao:WeatherForecastDAO by lazy {
        val db:AppDataBase=AppDataBase.getInstance(context)
        db.weatherForecastDAO()
    }
    private val dao_2:AlertDao by lazy {
        val db:AppDataBase=AppDataBase.getInstance(context)
        db.alertDao()
    }



    override suspend fun insertCurrentWeather(weatherForecast: WeatherForecast): Long {
        return dao.insertCurrentWeather(weatherForecast)
    }

    override suspend fun getStoredWeatherForecast(): Flow<WeatherForecast>? {
        return dao.getStoredWeatherForecast()
    }

    override suspend fun getAlertByCompositeKey(
        lat: Double,
        lon: Double,
        startDate: Long
    ): Flow<Alert> {
        return dao_2.getAlertByCompositeKey(lat,lon,startDate)
    }

    override suspend fun getAlerts(): Flow<List<Alert>> {
        return dao_2.getAlerts()
    }

    override suspend fun insertAlert(alert: Alert): Long {
        return dao_2.insertAlert(alert)
    }

    override suspend fun deleteAlert(alert: Alert) {
        return dao_2.deleteAlert(alert)
    }

}