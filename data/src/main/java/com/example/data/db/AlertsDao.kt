package com.example.data.db

import androidx.room.*
import com.example.data.models.UserAlert
import com.example.data.utils.Constants

@Dao
interface AlertsDao {
    @get:Query("Select * from ${Constants.userAlertTableName}")
    val getUserAlerts: List<UserAlert>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserAlerts(userAlerts: UserAlert)

    @Delete
    fun deleteUserAlerts(userAlerts: UserAlert)
}