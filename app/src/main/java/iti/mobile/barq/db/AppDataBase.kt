package iti.mobile.barq.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import iti.mobile.barq.model.WeatherForecast

@Database(entities = arrayOf(WeatherForecast::class), version = 1)
@TypeConverters(DataConverter::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun weatherForecastDAO(): WeatherForecastDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null


        fun getInstance(context: Context): AppDataBase{
            return INSTANCE ?:  synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext, AppDataBase::class.java, "weather_forecast_database"
                )
                    .build()
                INSTANCE=instance
                //return instance
                instance }
        }
    }
}