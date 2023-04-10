package iti.mobile.barq.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import iti.mobile.barq.model.WeatherForecast
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.Is
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class WeatherForecastDAOTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var weatherForecastDAO: WeatherForecastDAO
    private lateinit var database: AppDataBase
    private lateinit var currentWeather:WeatherForecast

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).allowMainThreadQueries().build()

        weatherForecastDAO = database.weatherForecastDAO()
         currentWeather = WeatherForecast(lat = 31.225904,lon=30.00195,null,null,null,null,null)

    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun getStoredWeatherForecast_insertCurrentWeather_checkLatitudeValue()= runBlockingTest  {
        // given
        weatherForecastDAO.insertCurrentWeather(currentWeather)
        // When
        val storedWeather = weatherForecastDAO.getStoredWeatherForecast()?.first()
        // Then
        assertThat(storedWeather?.lat, `is`(31.225904))
    }

    @Test
    fun insertCurrentWeather_insertCurrentWeather_checkLongitudeValue() = runBlockingTest{
        // When
        weatherForecastDAO.insertCurrentWeather(currentWeather)
        // Then
        val storedWeather = weatherForecastDAO.getStoredWeatherForecast()?.first()
        assertThat(storedWeather?.lon, `is`(30.00195))
    }
}