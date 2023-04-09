package iti.mobile.barq.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iti.mobile.barq.model.Constants
import iti.mobile.barq.model.IRepository
import iti.mobile.barq.model.WeatherForecast
import iti.mobile.barq.network.APIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(  private val _irepo: IRepository): ViewModel() {

    private var _currentWeatherForecast= MutableStateFlow<APIState>(APIState.Loading)
    val currentWeatherForecast= _currentWeatherForecast.asStateFlow()

    private var _lastWeatherForecast= MutableStateFlow<APIState>(APIState.Loading)
    val lastWeatherForecast= _lastWeatherForecast.asStateFlow()


    fun getCurrentWeather(
        latitude: String,
        longitude: String,
        language: String,
        unitOfMeasurement: String
    )
    {
        viewModelScope.launch(Dispatchers.IO){

            _irepo.getCurrentWeather(
                latitude=latitude,
                longitude=longitude,
                language=language,
                unitOfMeasurement=unitOfMeasurement
            ).catch { e->
                _currentWeatherForecast.value=APIState.Failure(e)
            }.collectLatest{ weatherForecast->
                _currentWeatherForecast.value=APIState.Success(weatherForecast)

            }

        }
    }


    fun insertCurrentWeather( weatherForecast: WeatherForecast){
        viewModelScope.launch(Dispatchers.IO){
            _irepo.insertCurrentWeather(weatherForecast)
        }
    }

    fun getStoredWeatherForecast(){
        viewModelScope.launch(Dispatchers.IO){
            val storedWeatherForecast=_irepo.getStoredWeatherForecast()?.catch { e->
                _lastWeatherForecast.value=APIState.Failure(e)
            }?.collectLatest{ weatherForecast->
                _lastWeatherForecast.value=APIState.Success(weatherForecast)

            }
        }
    }
}