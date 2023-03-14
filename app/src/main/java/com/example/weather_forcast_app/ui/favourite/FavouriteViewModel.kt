package com.example.weather_forcast_app.ui.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.FavouriteCityEntity
import com.example.domain.useCases.AddCityToFavourite
import com.example.domain.useCases.DeleteCityFromFavourite
import com.example.domain.useCases.GetAllFavouriteCities
import com.example.domain.utils.ResultResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val getAllFavouriteCities: GetAllFavouriteCities,private val addCityToFavourite: AddCityToFavourite,private val deleteCityFromFavourite: DeleteCityFromFavourite) : ViewModel() {
    private val _favouriteList: MutableStateFlow<ResultResponse<List<FavouriteCityEntity>>> = MutableStateFlow(ResultResponse.OnLoading(true))
    val favouriteList: StateFlow<ResultResponse<List<FavouriteCityEntity>>> = _favouriteList
    private var _addedSuccessfully = MutableStateFlow<String>("")
    val addedSuccessfully: StateFlow<String> = _addedSuccessfully
    private var _deletedSuccessfully = MutableStateFlow<String>("")
    val deletedSuccessfully: StateFlow<String> = _deletedSuccessfully
    fun getFavouriteList(){
        viewModelScope.launch{
           getAllFavouriteCities().flowOn(Dispatchers.IO).collect{
               _favouriteList.value=it
           }


        }
    }
    fun addFavourite(favourite:FavouriteCityEntity){
        viewModelScope.launch{
            addCityToFavourite(favourite).flowOn(Dispatchers.IO).collect{
                    it ->
                when(it){
                    is ResultResponse.OnSuccess->{
                        _addedSuccessfully.value="added successfully"

                    }

                    else -> {}
                }
            }
        }
    }
    fun deleteFromFav(favourite:FavouriteCityEntity){
        viewModelScope.launch{
            deleteCityFromFavourite(favourite).flowOn(Dispatchers.IO).collect{
                    it ->
                when(it){
                    is ResultResponse.OnSuccess->{
                        _deletedSuccessfully.value="deleted successfully"

                    }

                    else -> {}
                }
            }
        }
    }

}
