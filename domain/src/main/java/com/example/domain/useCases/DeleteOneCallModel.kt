package com.example.domain.useCases

import com.example.domain.entities.OneCall
import com.example.domain.repository.Repository

class DeleteOneCallModel(private val repository: Repository) {
    suspend operator fun invoke(oneCallModel: OneCall) = repository.deleteOneCallModel(oneCallModel)

}