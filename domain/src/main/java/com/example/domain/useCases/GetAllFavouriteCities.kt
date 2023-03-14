package com.example.domain.useCases

import com.example.domain.repository.Repository

class GetAllFavouriteCities(private val repository: Repository) {
    suspend operator fun invoke() = repository.getAllFavouriteCities()
}