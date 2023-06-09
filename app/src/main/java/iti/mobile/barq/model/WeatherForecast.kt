package iti.mobile.barq.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weather_forecast",primaryKeys = ["lat", "lon"])
data class WeatherForecast (
    val lat: Double,
    val lon: Double,
    val timezone: String?,
    val timezoneOffset: Long?,
    val current: Current?,
    val hourly: List<Current>?,
    val daily: List<Daily>?,
    val alerts: List<Alert>?=null,
    var countryName:String?=null
)

data class Current (
    val dt: Long,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val temp: Double,
    val feels_like: Double,
    val pressure: Long,
    val humidity: Long,
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Long,
    val visibility: Long,
    val wind_speed: Double,
    val windDeg: Long,
    val windGust: Double,
    val weather: List<Weather>,
    val pop: Double? = null
)

data class Weather (
    val id: Long,
    val main: Main,
    val description: String,
    val icon: String
)



enum class Main {
    Clear,
    Clouds,
    Rain
}

data class Daily (
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val moonrise: Long,
    val moonset: Long,
    val moonPhase: Double,
    val temp: Temp,
    val feelsLike: FeelsLike,
    val pressure: Long,
    val humidity: Long,
    val dewPoint: Double,
    val windSpeed: Double,
    val windDeg: Long,
    val windGust: Double,
    val weather: List<Weather>,
    val clouds: Long,
    val pop: Double,
    val uvi: Double,
    val rain: Double? = null
)

data class FeelsLike (
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class Temp (
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

@Entity(tableName = "Alert",primaryKeys = ["startTime", "lat","lon"])
class Alert(
    var startTime: Long,
    var endTime:Long,
    var startDate: Long,
    var endDate:Long,
    var lat:Double,
    var lon:Double,
    var cityName:String
)