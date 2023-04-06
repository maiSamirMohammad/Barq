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
        lat: String?,
        lon: String?,
        lang: String,
        units: String
    ): Flow<WeatherForecast> {
        return flow {
            emit(remoteSource.getCurrentWeather( lat, lon, lang =lang, units = units))
        }
    }





}