package com.example.domain.useCases

import com.example.domain.repository.Repository

class GetUserAlertsById(private val repository: Repository) {
    suspend operator fun invoke(id: Int) = repository.getUserAlertsById(id)
}