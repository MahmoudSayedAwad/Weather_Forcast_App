package com.example.data.db

import androidx.room.*
import com.example.data.models.OneCallResponse
import com.example.data.utils.Constants

@Dao
interface OneCallDao {
    @Query("Select * from ${Constants.oneCallTableName}")
    suspend fun getOneCallModel():List<OneCallResponse>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertOneCallModel(oneCallModel: OneCallResponse)
    @Delete
    suspend fun deleteOneCallModel(OneCallModel:OneCallResponse)
}