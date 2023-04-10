package iti.mobile.barq.alert.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iti.mobile.barq.model.Alert
import iti.mobile.barq.model.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AlertViewModel(  private val _irepo: IRepository): ViewModel() {

    private var _storedAlert= MutableStateFlow<List<Alert>>(emptyList())
    val storedAlert= _storedAlert.asStateFlow()

    fun getAlerts(){
        viewModelScope.launch(Dispatchers.IO){
            val storedAlerts=_irepo.getAlerts().collect{
                _storedAlert.value=it

            }
        }
    }

    fun deleteAlert(alert: Alert){
        viewModelScope.launch (Dispatchers.IO){
            _irepo.deleteAlert(alert)

        }
    }

    fun insertAlert(alert: Alert){
        viewModelScope.launch (Dispatchers.IO){
            _irepo.insertAlert(alert)

        }
    }







}