package com.example.domain.entities

data class UserAlertsEntity(
    var id: Int?,
    var startDate: Long,
    var endDate: Long,
    var type: String
)