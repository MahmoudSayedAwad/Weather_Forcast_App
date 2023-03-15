package com.example.weather_forcast_app.ui.home

import android.content.Context
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.OneCall
import com.example.domain.useCases.GetCurrentWeatherUseCase
import com.example.domain.useCases.InsertOneCallModel
import com.example.domain.utils.ResultResponse
import com.example.weather_forcast_app.utils.Constants
import com.example.weather_forcast_app.utils.LocationHelper
import com.example.weather_forcast_app.utils.SharedPrefManger
import com.example.weather_forcast_app.utils.getDisplayCurrentLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val sharedPrefManger: SharedPrefManger,
    @ApplicationContext var context: Context,
    private val insertOneCallModel: InsertOneCallModel

) : ViewModel() {
    private var _currentWeather =
        MutableStateFlow<ResultResponse<OneCall>>(ResultResponse.OnLoading(true))
    val currentWeather: StateFlow<ResultResponse<OneCall>> = _currentWeather
    private var _location = LocationHelper(context)
    val location = _location
    private var _cityName = MutableLiveData<String>()
    val cityName: LiveData<String> = _cityName


    private lateinit var listener: OnSharedPreferenceChangeListener
    fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        measurementUnit: String,
        language: String,
        apiKey: String
    ) {
        viewModelScope.launch {
            getCityName(latitude, longitude)

            getCurrentWeatherUseCase(latitude, longitude, measurementUnit, language, apiKey).flowOn(
                Dispatchers.IO
            ).collect {
                _currentWeather.value = it
            }
        }
    }

/*    fun registerOnSharedPrefChanged() {

        listener = OnSharedPreferenceChangeListener { _, key ->
            if (key == Constants.IS_LOCATION_SET) {
                getCurrentWeather(getLatitude(),getLongitude(),getCurrentTempMeasurementUnit(),getLanguage(),"67ca8d4acae59d540ea421e817caf1bb")
            }
        }
        sharedPrefManger.sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

    }

    fun getLocationAndSaveItInSharedPref(activity: Activity, context: Context) {
      //  LocationHelper().getLocation(activity, context, sharedPrefManger)
    }*/

    fun unRegisterOnSharedPreferenceChangeListener() {
        if (this::listener.isInitialized) sharedPrefManger.sharedPreferences.unregisterOnSharedPreferenceChangeListener(
            listener
        )
    }
    fun getLocationMethod():String {

         return   sharedPrefManger.getStringValue(
                Constants.LOCATION_METHOD,
                Constants.LOCATION_METHOD_GPS
            )


    }

    fun getLongitude(): Double {
        return sharedPrefManger.getFloatValue(
            Constants.LONGITUDE, 0f
        ).toDouble()
    }

    fun getLatitude(): Double {
        return sharedPrefManger.getFloatValue(Constants.LATITUDE, 0f).toDouble()
    }

    fun setLongitude(longitude: Float) {
        sharedPrefManger.setValue(
            Constants.LONGITUDE, longitude
        )
    }

    fun setLatitude(latitude: Float) {
        sharedPrefManger.setValue(Constants.LATITUDE, latitude)
    }

    fun getCurrentTempMeasurementUnit(): String {

        return sharedPrefManger.getStringValue(
            Constants.MEASUREMENT_UNIT, Constants.MEASUREMENT_UNIT_STANDARD
        )

    }

    fun getLanguage(): String {


        return sharedPrefManger.getStringValue(
            Constants.APPLICATION_LANGUAGE, getDisplayCurrentLanguage()
        )

    }

    fun getCityName(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val geocoder = Geocoder(context, Locale.getDefault())
            val list = geocoder.getFromLocation(
                latitude, longitude, 1
            ) as List<Address>
            _cityName.postValue(list[0].adminArea)

        }

    }

    fun getWindSpeedUnit(): String {

        return sharedPrefManger.getStringValue(
            Constants.WIND_SPEED_UNIT, Constants.WIND_SPEED_UNIT_M_P_S
        )


    }

    suspend fun addToDatabase(oneCall: OneCall) {
        viewModelScope.launch {
            insertOneCallModel(oneCall).flowOn(Dispatchers.IO).collect {

            }
        }

    }
}

