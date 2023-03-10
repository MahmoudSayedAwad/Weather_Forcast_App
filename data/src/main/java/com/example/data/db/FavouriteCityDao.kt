package com.example.data.db

import androidx.room.*
import com.example.data.models.FavouriteCity
import com.example.data.utils.Constants


@Dao
    interface FavouriteCityDao {
        @Query("Select * from ${Constants.favouriteCityTableName}")
       suspend fun getFavourite():List<FavouriteCity>
        @Insert(onConflict = OnConflictStrategy.IGNORE)
       suspend fun addCityToFavourite(favouriteCity: FavouriteCity)
        @Delete
       suspend fun deleteCityFromFavourite(favouriteCity: FavouriteCity)
    }
