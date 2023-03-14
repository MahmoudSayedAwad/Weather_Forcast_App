package com.example.domain.useCases

import com.example.domain.entities.UserAlertsEntity
import com.example.domain.repository.Repository

class InsertUserAlerts(private val repository: Repository) {
    suspend operator fun invoke(userAlert: UserAlertsEntity) =
        repository.insertUserAlerts(userAlert)
}