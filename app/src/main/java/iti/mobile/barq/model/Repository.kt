package iti.mobile.barq.model

import iti.mobile.barq.db.ILocalSource
import iti.mobile.barq.network.IRemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository private constructor(var localSource:ILocalSource,var remoteSource: IRemoteSource) :
    IRepository {

    companion object{
        private var instance: Repository?=null
        fun getInstance(localSource: ILocalSource, remoteSource: IRemoteSource):Repository{
            return instance?: synchronized(this){
                val temp=Repository(localSource,remoteSource)
                instance=temp
                temp
            }

        }
    }

    //from network
    override suspend fun getCurrentWeather(
        latitude: String,
        longitude: String,
        language: String ,
        unitOfMeasurement: String
    ): Flow<WeatherForecast> {
        return flow {
            emit(
                remoteSource.getCurrentWeather(
                latitude=latitude,
                longitude=longitude,
                language=language ,
                unitOfMeasurement=unitOfMeasurement)
            )
        }
    }

    override suspend fun insertCurrentWeather(weatherForecast: WeatherForecast) {
        localSource.insertCurrentWeather(weatherForecast)
    }



    override suspend fun getStoredWeatherForecast(): Flow<WeatherForecast>? {
        return localSource.getStoredWeatherForecast()
    }

    override suspend fun getAlertByCompositeKey(
        lat: Double,
        lon: Double,
        startDate: Long
    ): Flow<Alert> {
        return localSource.getAlertByCompositeKey(lat,lon,startDate)
    }

    override suspend fun getAlerts(): Flow<List<Alert>> {
        return localSource.getAlerts()
    }

    override suspend fun insertAlert(alert: Alert): Long {
        return localSource.insertAlert(alert)
    }

    override suspend fun deleteAlert(alert: Alert) {
        return localSource.deleteAlert(alert)
    }


}