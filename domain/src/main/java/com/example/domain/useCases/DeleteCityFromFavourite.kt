package com.example.domain.useCases

import com.example.domain.entities.FavouriteCityEntity
import com.example.domain.repository.Repository

class DeleteCityFromFavourite(private val repository: Repository) {
    suspend operator fun invoke(favouriteCityEntity: FavouriteCityEntity) =
        repository.deleteCityFromFavourite(favouriteCityEntity)

}