package com.example.weather_forcast_app.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.Daily
import com.example.weather_forcast_app.R
import com.example.weather_forcast_app.utils.Constants
import com.example.weather_forcast_app.utils.getDateTime
import com.squareup.picasso.Picasso

class DailyAdapter(private var context: Context, private var days: List<Daily>) :
    RecyclerView.Adapter<DailyAdapter.ViewHolder>() {
    fun setDays(days: List<Daily>) {
        this.days = days
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.daily_raw, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (days.isNotEmpty()) {


            holder.dayForCurrentWeather.text = getDateTime(days.get(position).dt, "MMM d", "en")
            holder.weatherDescriptionDay.text = days.get(position).weather[0].description
            holder.temperatureDay.text =
                "${days.get(position).temp.max.toInt()}/ ${days.get(position).temp.min.toInt()}"
            Picasso.get().load("${Constants.IMG_URL}${days.get(position).weather[0].icon}@4x.png")
                .into(holder.tempIconDayRow)
        }

    }

    override fun getItemCount(): Int {
        return days.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayForCurrentWeather: TextView
            get() = itemView.findViewById(R.id.tvDate)
        val weatherDescriptionDay: TextView
            get() = itemView.findViewById(R.id.tvWeatherDescription)
        val temperatureDay: TextView
            get() = itemView.findViewById(R.id.tvTempMinMax)
        val tempIconDayRow: ImageView
            get() = itemView.findViewById(R.id.ivDayIcon)
    }
}