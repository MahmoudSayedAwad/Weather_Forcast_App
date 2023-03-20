package com.example.domain.useCases

import com.example.domain.entities.UserAlertsEntity
import com.example.domain.repository.Repository

class DeleteUserAlertsById(private val repository: Repository) {
    suspend operator fun invoke(id: Int) = repository.deleteUserAlertsById(id)
}