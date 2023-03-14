package com.example.domain.useCases

import com.example.domain.entities.OneCall
import com.example.domain.entities.UserAlertsEntity
import com.example.domain.repository.Repository

class DeleteUserAlerts(private val repository: Repository) {
    suspend operator fun invoke(userAlert: UserAlertsEntity) = repository.deleteUserAlerts(userAlert)
}