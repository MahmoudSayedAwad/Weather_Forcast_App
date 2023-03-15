package com.example.weather_forcast_app.ui.favourite

import com.example.domain.entities.FavouriteCityEntity

interface FavouriteInterface {
    fun delete(fav: FavouriteCityEntity, position: Int)
    fun goToDetails(fav:FavouriteCityEntity)
}