package com.example.domain.useCases

import com.example.domain.repository.Repository

class GetUserAlerts(private val repository: Repository) {
    suspend operator fun invoke() =
        repository.getUserAlerts()
}