package com.example.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.utils.Constants

@Entity(tableName = Constants.userAlertTableName)
data class UserAlert(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "startDate")
    var startDate: Long,
    @ColumnInfo(name = "endDate")
    var endDate: Long,
    //alert or notification
    @ColumnInfo(name = "type")
    var type: String
)