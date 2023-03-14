package com.example.domain.entities

data class UserAlertsEntity(
    var id: Int, var startDate: Long, var endDate: Long,
    //alert or notification
    var type: String
)