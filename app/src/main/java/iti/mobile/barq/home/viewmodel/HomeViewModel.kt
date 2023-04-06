package iti.mobile.barq.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iti.mobile.barq.model.Constants
import iti.mobile.barq.model.IRepository
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

    init {

    }

    fun getCurrentWeather(
        lat: String?, lon: String?, lang: String = Constants.LANGUAGE_ENGLISH, units: String = Constants.UNITS_DEFAULT
    )
    {
        viewModelScope.launch(Dispatchers.IO){

            _irepo.getCurrentWeather(
                lat, lon, lang = lang, units = units
            ).catch { e->
                _currentWeatherForecast.value=APIState.Failure(e)
            }.collectLatest{ weatherForecast->
                _currentWeatherForecast.value=APIState.Success(weatherForecast)

            }

        }
    }
}