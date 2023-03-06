package com.example.weather_forcast_app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.OneCall
import com.example.domain.useCases.GetCurrentWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase) : ViewModel() {
    private var _currentWeather= MutableStateFlow<OneCall?>(null)
    val currentWeather: StateFlow<OneCall?> = _currentWeather
    fun getCurrentWeather(latitude:Double, longitude:Double, measurementUnit:String, language:String, apiKey:String){
        viewModelScope.launch {
           _currentWeather.value= getCurrentWeatherUseCase(latitude, longitude, measurementUnit, language, apiKey)
        }
    }
}