package com.example.weather_forcast_app.ui

import androidx.lifecycle.ViewModel
import com.example.weather_forcast_app.utils.Constants
import com.example.weather_forcast_app.utils.SharedPrefManger
import com.example.weather_forcast_app.utils.getDisplayCurrentDefaultLanguage
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor (private val sharedPrefManger: SharedPrefManger) : ViewModel() {
    fun setDataToSharedPrefInFirstTime(){
        if(sharedPrefManger.getBooleanValue(Constants.IS_FIRST_TIME,false)){
            sharedPrefManger.setValue(Constants.MEASUREMENT_UNIT,Constants.MEASUREMENT_UNIT_STANDARD)
            sharedPrefManger.setValue(Constants.WIND_SPEED_UNIT,Constants.WIND_SPEED_UNIT_M_P_S)
            sharedPrefManger.setValue(Constants.APPLICATION_LANGUAGE, getDisplayCurrentDefaultLanguage())
            sharedPrefManger.setValue(Constants.LATITUDE,0.0)
            sharedPrefManger.setValue(Constants.LONGITUDE,0.0)
            sharedPrefManger.setValue(Constants.LOCATION_METHOD,Constants.LOCATION_METHOD_GPS)
            sharedPrefManger.setValue(Constants.NOTIFICATION,true)
            sharedPrefManger.setValue(Constants.IS_FIRST_TIME,false)
        }
    }
    fun getLanguage() :String{
          return sharedPrefManger.getStringValue(Constants.APPLICATION_LANGUAGE, getDisplayCurrentDefaultLanguage())
    }
    fun getMeasurement() :String{
        return sharedPrefManger.getStringValue(Constants.MEASUREMENT_UNIT, Constants.MEASUREMENT_UNIT_METRIC)
    }



}