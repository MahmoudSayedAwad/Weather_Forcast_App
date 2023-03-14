package com.example.weather_forcast_app.ui.alerts

import com.example.domain.entities.UserAlertsEntity

interface OnDeleteAlert {
    fun delete(alert: UserAlertsEntity)
}