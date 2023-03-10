package com.example.domain.entities

data class OneCall(
    var current: Current,
    var daily: List<Daily>,
    var hourly: List<Hourly>,
    var lat: Double,
    var lon: Double,
    var minutely: List<Minutely>,
    var timezone: String,
    var timezone_offset: Int,
    var alerts: List<Alert>
)