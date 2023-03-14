package com.example.weather_forcast_app.ui.favourite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.entities.FavouriteCityEntity
import com.example.weather_forcast_app.databinding.FavouriteRowBinding

class FavouriteAdapter(var context: Context?, var fav:FavouriteInterface): ListAdapter<FavouriteCityEntity, FavouriteViewHolder>(FavouriteDiffUtil()) {
    lateinit var binding: FavouriteRowBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding= FavouriteRowBinding.inflate(inflater,parent,false)
        return FavouriteViewHolder(binding)

    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val current=getItem(position)
        holder.binding.deleteThisRow.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                fav.delete(current)
            }

        })
        holder.binding.textCityFavName.text=current.cityName

    }
}