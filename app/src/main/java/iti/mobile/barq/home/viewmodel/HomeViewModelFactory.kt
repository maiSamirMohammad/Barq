package iti.mobile.barq.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import iti.mobile.barq.model.IRepository

class HomeViewModelFactory(private val repository: IRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(repository) as T
        }
        else {
            throw java.lang.IllegalArgumentException("ViewModel class not found")
        }
    }
}