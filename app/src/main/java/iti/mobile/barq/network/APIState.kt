package iti.mobile.barq.network

import iti.mobile.barq.model.WeatherForecast


sealed class  APIState{
    class Success(var data : WeatherForecast):APIState()
    class Failure(val msg : Throwable):APIState()
    object Loading :APIState()
}
