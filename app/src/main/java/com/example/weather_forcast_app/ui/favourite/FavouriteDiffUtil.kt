package com.example.weather_forcast_app.ui.favourite
import androidx.recyclerview.widget.DiffUtil
import com.example.domain.entities.FavouriteCityEntity

class FavouriteDiffUtil:DiffUtil.ItemCallback<FavouriteCityEntity>(){
    override fun areItemsTheSame(oldItem: FavouriteCityEntity, newItem: FavouriteCityEntity): Boolean {
        return oldItem.cityName==newItem.cityName
    }


    override fun areContentsTheSame(oldItem: FavouriteCityEntity, newItem: FavouriteCityEntity): Boolean {
        return oldItem==newItem
    }
}