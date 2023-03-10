package com.example.data.models

data class AlertResponse(
    var description: String?,
    var end: Int?,
    var event: String?,
    var sender_name: String?,
    var start: Int?,
    var tags: List<String?>?
)