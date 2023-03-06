package com.example.weather_forcast_app.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.Hourly
import com.example.weather_forcast_app.R
import com.example.weather_forcast_app.utils.Constants
import com.example.weather_forcast_app.utils.getDateTime
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class HourlyAdapter(
    private var hours: List<Hourly>,
    private val context: Context,
) : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {
    fun sethours(hours:  List<Hourly>) {
        this.hours = hours
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.hourly_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.hourTime.text= getDateTime(hours.get(position).dt,"hh a","en")
        holder.tempHour.text=hours.get(position).temp.toString()
        Picasso.get().load("${Constants.IMG_URL}${hours.get(position).weather[0].icon}@4x.png").into(holder.tempIconHour)


    }

    override fun getItemCount(): Int = hours.size
    inner class ViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tempIconHour
            get() = itemView.findViewById<CircleImageView>(R.id.tempIconHourRow)
        val hourTime: TextView
            get() = itemView.findViewById(R.id.hourTime)
        val tempHour: TextView
            get() = itemView.findViewById(R.id.tempHour)

    }
}