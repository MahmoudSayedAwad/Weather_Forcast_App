package com.example.weather_forcast_app.ui.favourite

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.FavouriteCityEntity
import com.example.domain.useCases.AddCityToFavourite
import com.example.domain.useCases.DeleteCityFromFavourite
import com.example.domain.useCases.GetAllFavouriteCities
import com.example.domain.utils.ResultResponse
import com.example.weather_forcast_app.utils.Constants
import com.example.weather_forcast_app.utils.SharedPrefManger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getAllFavouriteCities: GetAllFavouriteCities,
    private val addCityToFavourite: AddCityToFavourite,
    private val deleteCityFromFavourite: DeleteCityFromFavourite,
    private val sharedPrefManger: SharedPrefManger
) : ViewModel() {
    private val _favouriteList: MutableStateFlow<ResultResponse<List<FavouriteCityEntity>>> =
        MutableStateFlow(ResultResponse.OnLoading(true))
    val favouriteList: StateFlow<ResultResponse<List<FavouriteCityEntity>>> = _favouriteList
    private var _addedSuccessfully = MutableStateFlow<String>("")
    val addedSuccessfully: StateFlow<String> = _addedSuccessfully
    private var _deletedSuccessfully = MutableStateFlow<String>("")
    val deletedSuccessfully: StateFlow<String> = _deletedSuccessfully
    fun getFavouriteList() {
        viewModelScope.launch {
            getAllFavouriteCities().flowOn(Dispatchers.IO).collect {
                _favouriteList.value = it
            }


        }
    }

    fun addFavourite(favourite: FavouriteCityEntity) {
        viewModelScope.launch {
            addCityToFavourite(favourite).flowOn(Dispatchers.IO).collect { it ->
                when (it) {
                    is ResultResponse.OnSuccess -> {
                        _addedSuccessfully.value = "added successfully"

                    }

                    else -> {}
                }
            }
        }
    }

    fun deleteFromFav(favourite: FavouriteCityEntity){
        viewModelScope.launch {
            deleteCityFromFavourite(favourite).flowOn(Dispatchers.IO).collect { it ->
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
