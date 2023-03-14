package com.example.domain.useCases

import com.example.domain.entities.OneCall
import com.example.domain.repository.Repository

class InsertOneCallModel(private val repository: Repository) {
    suspend operator fun invoke(oneCallModel: OneCall) =
        repository.insertOneCallModel(oneCallModel)
}