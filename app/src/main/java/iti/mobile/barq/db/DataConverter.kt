package iti.mobile.barq.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import iti.mobile.barq.model.Current
import iti.mobile.barq.model.Daily
import java.lang.reflect.Type

class DataConverter {
    @TypeConverter
    fun fromcurrent(current: Current?): String? {
        if (current == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Current>() {}.type
        return gson.toJson(current, type)
    }

    @TypeConverter
    fun toCurrent(currentStr: String?): Current? {
        if (currentStr == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Current>() {}.type
        return gson.fromJson(currentStr, type)
    }




    @TypeConverter
    fun fromHourly(hourly: List<Current?>?): String? {
        if (hourly == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Current?>?>() {}.type
        return gson.toJson(hourly, type)
    }

    @TypeConverter
    fun toHourly(hourlyString: String?): List<Current>? {
        if (hourlyString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Current?>?>() {}.type
        return gson.fromJson(hourlyString, type)
    }

    @TypeConverter
    fun fromDailyList(daily: List<Daily?>?): String? {
        if (daily == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Daily?>?>() {}.type
        return gson.toJson(daily, type)
    }

    @TypeConverter
    fun toDailyList(dailyString: String?): List<Daily>? {
        if (dailyString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Daily?>?>() {}.type
        return gson.fromJson(dailyString, type)
    }

}
