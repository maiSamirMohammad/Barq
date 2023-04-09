package iti.mobile.barq.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import iti.mobile.barq.model.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNot
import org.hamcrest.core.IsNot.not
import org.hamcrest.core.IsNull
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class WeatherForecastServiceTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()



    @Test
    fun getCurrentWeather_requestParticularLocationForecast_ResponseIsNotNull() = runBlocking{
        //given
        val weatherForecastService:WeatherForecastService=
            RetrofitHelper.getInstance().create(WeatherForecastService::class.java)
        //when
        val result =weatherForecastService.getCurrentWeather(
            latitude = "31.225904",
            longitude = "30.00195",
            appId = Constants.APP_ID,
            language = Constants.LANGUAGE_ENGLISH,
            unitOfMeasurement = Constants.UNITS_DEFAULT
        )
        //then
        assertThat(result, not(IsNull.nullValue()))

    }
}