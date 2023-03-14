package com.example.weather_forcast_app.ui.alerts





import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.entities.UserAlertsEntity
import com.example.weather_forcast_app.databinding.AlertRowBinding
import com.example.weather_forcast_app.utils.getDateTime
import com.example.weather_forcast_app.utils.getDateTimeLong

class AlertsAdapter(var context: Context?,var language:String,
                    var onDeleteAlert:  OnDeleteAlert): ListAdapter<UserAlertsEntity, AlertsViewHolder>(
    AlertsDiffUtile()
) {
    lateinit var binding: AlertRowBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertsViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding= AlertRowBinding.inflate(inflater,parent,false)
        return AlertsViewHolder(binding)

    }

    override fun onBindViewHolder(holder: AlertsViewHolder, position: Int) {
        val current=getItem(position)
        holder.binding.deleteThisAlertRow.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onDeleteAlert.delete(current)
            }

        })
        holder.binding.startAlertDateTextViewRow.text= getDateTimeLong(current.startDate,"EEE, MMM d",language)
        holder.binding.startAlertTimeTextViewRow.text = getDateTimeLong(current.startDate, "hh:mm a", language)

        holder.binding.endAlertTimeTextViewRow.text = getDateTimeLong(current.endDate, "hh:mm a", language)
        holder.binding.endAlertDateTextViewRow.text= getDateTimeLong(current.endDate, "EEE, MMM d", language)

    }
}