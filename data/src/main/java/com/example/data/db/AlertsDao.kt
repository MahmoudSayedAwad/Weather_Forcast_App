package com.example.data.db

import androidx.room.*
import com.example.data.models.UserAlert
import com.example.data.utils.Constants

@Dao
interface AlertsDao {
    @Query("Select * from ${Constants.userAlertTableName}")
    suspend fun getUserAlerts(): List<UserAlert>
    @Query("Select * from ${Constants.userAlertTableName} where id = :id")
    suspend fun getUserAlertsById(id: Int): UserAlert

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAlerts(userAlerts: UserAlert):Long

    @Delete
    suspend fun deleteUserAlerts(userAlerts: UserAlert)

    @Query("DELETE FROM ${Constants.userAlertTableName} where id = :id")
    fun deleteUserAlertsById(id: Int)
}