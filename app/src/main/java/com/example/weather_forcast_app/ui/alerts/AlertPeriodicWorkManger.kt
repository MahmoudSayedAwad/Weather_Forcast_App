package com.example.weather_forcast_app.ui.alerts

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.domain.entities.OneCall
import com.example.domain.entities.UserAlertsEntity
import com.example.domain.useCases.DeleteUserAlertsById
import com.example.domain.useCases.GetCurrentWeatherUseCase
import com.example.domain.useCases.GetUserAlertsById
import com.example.domain.utils.ResultResponse
import com.example.weather_forcast_app.utils.Constants
import com.example.weather_forcast_app.utils.SharedPrefManger
import com.example.weather_forcast_app.utils.getDisplayCurrentDefaultLanguage
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*
import java.util.concurrent.TimeUnit

@HiltWorker
class AlertPeriodicWorkManger @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    var sharedPrefManger: SharedPrefManger,
    var deleteAlertById: DeleteUserAlertsById,
    var getUserAlertsById: GetUserAlertsById,
    var getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : CoroutineWorker(appContext, workerParams) {
    var currentWeather: MutableStateFlow<OneCall?> = MutableStateFlow(null)

    override suspend fun doWork(): Result {
        if (!isStopped) {
            val id = inputData.getLong("id", -1)
            getData(id.toInt())
        }
        return Result.success()
    }

    private fun getLongitude(): Double {
        return sharedPrefManger.getFloatValue(
            Constants.LONGITUDE, 0f
        ).toDouble()
    }

    private fun getLatitude(): Double {
        return sharedPrefManger.getFloatValue(Constants.LATITUDE, 0f).toDouble()
    }

    private fun getCurrentTempMeasurementUnit(): String {

        return sharedPrefManger.getStringValue(
            Constants.MEASUREMENT_UNIT, Constants.MEASUREMENT_UNIT_STANDARD
        )

    }

    fun getLanguage(): String {


        return sharedPrefManger.getStringValue(
            Constants.APPLICATION_LANGUAGE, getDisplayCurrentDefaultLanguage()
        )

    }

    private suspend fun getData(id: Int) {
        // request data from room or network

        getCurrentWeatherUseCase(
            getLatitude(),
            getLongitude(),
            getCurrentTempMeasurementUnit(),
            getLanguage(),
            "67ca8d4acae59d540ea421e817caf1bb"
        ).collect {
            when (it) {
                is ResultResponse.OnSuccess -> {
                    currentWeather.value = it.data
                }
                else -> {}
            }

        }


        val alert = getUserAlertsById(id)
        delay(500)

        Log.i("tag", alert.startDate.toString())
        if (checkTime(alert)) {
            val delay: Long = getDelay(alert)
            if (currentWeather.value?.alerts?.isEmpty()!!) {
                setOneTimeWorkManger(
                    delay,
                    alert.id,
                    currentWeather.value?.current?.weather?.get(0)?.description ?: "",
                )
            } else {
                setOneTimeWorkManger(
                    delay,
                    alert.id,
                    currentWeather.value?.alerts?.get(0)?.tags?.get(0) ?: "",
                )
            }
        } else {
            deleteAlertById(id)
            WorkManager.getInstance().cancelAllWorkByTag("$id")
        }
    }


    private fun setOneTimeWorkManger(delay: Long, id: Int?, description: String) {
        val data = Data.Builder()
        data.putString("description", description)

        val constraints = Constraints.Builder().setRequiresBatteryNotLow(true).build()


        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(
            OneTimeWorkManger::class.java,
        ).setInitialDelay(delay, TimeUnit.SECONDS).setConstraints(constraints)
            .setInputData(data.build()).build()

        WorkManager.getInstance(appContext).enqueueUniqueWork(
            "$id", ExistingWorkPolicy.REPLACE, oneTimeWorkRequest
        )
    }

    private fun getDelay(alert: UserAlertsEntity): Long {
        println(alert)
        val hour =
            TimeUnit.HOURS.toSeconds(Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toLong())
        val minute =
            TimeUnit.MINUTES.toSeconds(Calendar.getInstance().get(Calendar.MINUTE).toLong())
        return alert.startDate - ((hour + minute) - (2 * 3600L))
    }

    private fun checkTime(alert: UserAlertsEntity): Boolean {
        val currentTimeInMillis = Calendar.getInstance().timeInMillis
        return currentTimeInMillis >= alert.startDate && currentTimeInMillis <= alert.endDate
    }


}