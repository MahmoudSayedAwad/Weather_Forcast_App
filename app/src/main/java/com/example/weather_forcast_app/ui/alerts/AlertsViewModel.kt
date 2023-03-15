package com.example.weather_forcast_app.ui.alerts

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.UserAlertsEntity
import com.example.domain.useCases.DeleteUserAlerts
import com.example.domain.useCases.GetUserAlerts
import com.example.domain.useCases.InsertUserAlerts
import com.example.domain.utils.ResultResponse
import com.example.weather_forcast_app.utils.Constants
import com.example.weather_forcast_app.utils.SharedPrefManger
import com.example.weather_forcast_app.utils.getDisplayCurrentLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val getUserAlerts: GetUserAlerts,
    private val addUserAlerts: InsertUserAlerts,
    private val deleteUserAlerts: DeleteUserAlerts,
    private val sharedPrefManger: SharedPrefManger
) : ViewModel() {
    private val _alertsList: MutableStateFlow<ResultResponse<List<UserAlertsEntity>>> =
        MutableStateFlow(ResultResponse.OnLoading(true))
    val alertList: StateFlow<ResultResponse<List<UserAlertsEntity>>> = _alertsList
    private var _addedSuccessfully = MutableStateFlow<String>("")
    val addedSuccessfully: StateFlow<String> = _addedSuccessfully
    private var _deletedSuccessfully = MutableStateFlow<String>("")
    val deletedSuccessfully: StateFlow<String> = _deletedSuccessfully
    fun getAlertsList() {
        viewModelScope.launch {
            getUserAlerts().flowOn(Dispatchers.IO).collect {
                _alertsList.value = it
            }


        }
    }

    fun getLanguage(): String {

        return sharedPrefManger.getStringValue(
            Constants.APPLICATION_LANGUAGE, getDisplayCurrentLanguage()
        )

    }

    fun addUserAlert(alertsEntity: UserAlertsEntity) {
        viewModelScope.launch {
            addUserAlerts(alertsEntity).flowOn(Dispatchers.IO).collect { it ->
                when (it) {
                    is ResultResponse.OnSuccess -> {
                        _addedSuccessfully.value = "added successfully"

                    }

                    else -> {}
                }
            }
        }
    }

    fun deleteFromUserAlert(alertsEntity: UserAlertsEntity) {
        viewModelScope.launch {
            deleteUserAlerts(alertsEntity).flowOn(Dispatchers.IO).collect { it ->
                when (it) {
                    is ResultResponse.OnSuccess -> {
                        _deletedSuccessfully.value = "deleted successfully"

                    }

                    else -> {}
                }
            }
        }
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

    fun setLocationMethod(locationMethod: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedPrefManger.setValue(Constants.LOCATION_METHOD, locationMethod)
        }
    }

    fun setLatitude(latitude: Float) {
        sharedPrefManger.setValue(Constants.LATITUDE, latitude)
    }

    fun getCityName(
        latitude: Double, longitude: Double, context: Context, language: String
    ): List<Address> {
        val geocoder = Geocoder(context, Locale.getDefault())
        val list = geocoder.getFromLocation(
            latitude, longitude, 1
        ) as List<Address>
        return list

    }

}