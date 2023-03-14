package com.example.weather_forcast_app.ui.alerts

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.entities.UserAlertsEntity

class AlertsDiffUtile : DiffUtil.ItemCallback<UserAlertsEntity>() {
    override fun areItemsTheSame(oldItem: UserAlertsEntity, newItem: UserAlertsEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserAlertsEntity, newItem: UserAlertsEntity): Boolean {
        return oldItem == newItem
    }
}