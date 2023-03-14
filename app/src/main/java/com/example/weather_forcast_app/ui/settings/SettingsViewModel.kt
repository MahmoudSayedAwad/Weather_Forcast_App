package com.example.weather_forcast_app.ui.settings

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_forcast_app.utils.Constants
import com.example.weather_forcast_app.utils.Constants.APPLICATION_LANGUAGE
import com.example.weather_forcast_app.utils.Constants.APPLICATION_LANGUAGE_EN
import com.example.weather_forcast_app.utils.Constants.LOCATION_METHOD
import com.example.weather_forcast_app.utils.Constants.LOCATION_METHOD_GPS
import com.example.weather_forcast_app.utils.Constants.MEASUREMENT_UNIT
import com.example.weather_forcast_app.utils.Constants.MEASUREMENT_UNIT_STANDARD
import com.example.weather_forcast_app.utils.Constants.NOTIFICATION
import com.example.weather_forcast_app.utils.Constants.NOTIFICATION_Type
import com.example.weather_forcast_app.utils.Constants.NOTIFICATION_Type_NOTI
import com.example.weather_forcast_app.utils.Constants.WIND_SPEED_UNIT
import com.example.weather_forcast_app.utils.Constants.WIND_SPEED_UNIT_M_P_S
import com.example.weather_forcast_app.utils.SharedPrefManger
import com.example.weather_forcast_app.utils.getDisplayCurrentLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val sharedPrefManger: SharedPrefManger) :
    ViewModel() {

    private var _locationMethod = MutableLiveData<String>(LOCATION_METHOD_GPS)
    val locationMethod: LiveData<String> = _locationMethod

    private var _language = MutableLiveData<String>(APPLICATION_LANGUAGE_EN)
    val language: LiveData<String> = _language

    private var _currentTempMeasurementUnit = MutableLiveData<String>(MEASUREMENT_UNIT_STANDARD)
    val currentTempMeasurementUnit: LiveData<String> = _currentTempMeasurementUnit

    private var _windSpeedUnit = MutableLiveData<String>(WIND_SPEED_UNIT_M_P_S)
    val windSpeedUnit: LiveData<String> = _windSpeedUnit

    private var _notificationChecked = MutableLiveData<Boolean>(true)
    val notificationChecked: LiveData<Boolean> = _notificationChecked
    private var _notificationType = MutableLiveData<String>("")
    val notificationType: LiveData<String> = _notificationType

    private var _changedSuccessfully = MutableLiveData<String>("changedSuccessfully")
    val changedSuccessfully: LiveData<String> = _changedSuccessfully
    private lateinit var listener: OnSharedPreferenceChangeListener


    fun setLocationMethod(locationMethod: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedPrefManger.setValue(LOCATION_METHOD, locationMethod)
        }
    }

    fun getLocationMethod() {
        viewModelScope.launch(Dispatchers.IO) {
            _locationMethod.postValue(sharedPrefManger.getStringValue(LOCATION_METHOD, LOCATION_METHOD_GPS))

        }
    }

    fun setLanguage(language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedPrefManger.setValue(APPLICATION_LANGUAGE, language)
        }
    }

    fun getLanguage() {
        viewModelScope.launch(Dispatchers.IO) {
            _language.postValue( sharedPrefManger.getStringValue(APPLICATION_LANGUAGE, getDisplayCurrentLanguage()))

        }
    }

    fun setCurrentTempMeasurementUnit(measurementUnit: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedPrefManger.setValue(MEASUREMENT_UNIT, measurementUnit)
        }
    }

    fun getCurrentTempMeasurementUnit() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentTempMeasurementUnit.postValue(sharedPrefManger.getStringValue(
                MEASUREMENT_UNIT, MEASUREMENT_UNIT_STANDARD
            ))
        }
    }

    fun setWindSpeedUnit(windSpeedUnit: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedPrefManger.setValue(WIND_SPEED_UNIT, windSpeedUnit)
        }
    }

    fun getWindSpeedUnit() {
        viewModelScope.launch(Dispatchers.IO) {
            _windSpeedUnit.postValue(sharedPrefManger.getStringValue(WIND_SPEED_UNIT, WIND_SPEED_UNIT_M_P_S))

        }
    }

    fun setNotificationChecked(isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedPrefManger.setValue(NOTIFICATION, isChecked)
        }
    }

    fun getNotificationChecked() {
        viewModelScope.launch(Dispatchers.IO) {
            _notificationChecked.postValue(sharedPrefManger.getBooleanValue(NOTIFICATION, true))
        }
    }

    fun registerOnSharedPrefChanged() {
        listener = OnSharedPreferenceChangeListener { _, key ->
            when (key) {
                LOCATION_METHOD -> {
                    _changedSuccessfully.value = "changedSuccessfully"
                }
                APPLICATION_LANGUAGE -> {
                    _changedSuccessfully.value = "changedSuccessfully"
                }
                MEASUREMENT_UNIT -> {
                    _changedSuccessfully.value = "changedSuccessfully"
                }
                WIND_SPEED_UNIT -> {
                    _changedSuccessfully.value = "changedSuccessfully"
                }
                NOTIFICATION -> {
                    _changedSuccessfully.value = "changedSuccessfully"
                }
            }
        }
        sharedPrefManger.sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unRegisterOnSharedPreferenceChangeListener() {
        sharedPrefManger.sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun setNotificationType(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedPrefManger.setValue(NOTIFICATION_Type, type)
        }
    }
    fun getNotificationType() {
        viewModelScope.launch(Dispatchers.IO) {
            _notificationType.postValue(sharedPrefManger.getStringValue(NOTIFICATION_Type, NOTIFICATION_Type_NOTI))
        }
    }

}
