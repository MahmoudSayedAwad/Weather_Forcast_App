package com.example.data.db

import androidx.room.*
import com.example.data.models.UserAlert
import com.example.data.utils.Constants

@Dao
interface AlertsDao {
    @Query("Select * from ${Constants.userAlertTableName}")
    suspend fun getUserAlerts(): List<UserAlert>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAlerts(userAlerts: UserAlert)

    @Delete
    suspend fun deleteUserAlerts(userAlerts: UserAlert)
}