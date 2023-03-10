package com.example.domain.entities

data class Alert(


    var description: String,
    var end: Int,
    var event: String,
    var sender_name: String,
    var start: Int,
    var tags: List<String>

)