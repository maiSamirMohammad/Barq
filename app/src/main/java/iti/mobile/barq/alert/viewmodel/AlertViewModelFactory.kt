package iti.mobile.barq.alert.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import iti.mobile.barq.model.IRepository

class AlertViewModelFactory(private val repository: IRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AlertViewModel::class.java)) {
            AlertViewModel(repository) as T
        }
        else {
            throw java.lang.IllegalArgumentException("ViewModel class not found")
        }
    }
}