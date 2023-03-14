package com.example.data.mappers

import com.example.data.models.*
import com.example.data.utils.Constants
import com.example.data.utils.orZero
import com.example.domain.entities.*

fun OneCall?.fromDomain(): OneCallResponse {
    return OneCallResponse(
        this?.current.fromDomain(),
        this?.daily?.map { obj -> obj.fromDomain() }?.toList() ?: emptyList<DailyResponse>(),
        this?.hourly?.map { obj -> obj.fromDomain() }?.toList() ?: emptyList(),
        this?.lat?.orZero() ?: Constants.zero.toDouble(),
        this?.lon?.orZero() ?: Constants.zero.toDouble(),
        this?.minutely?.map { obj -> obj.fromDomain() }?.toList() ?: emptyList(),
        this?.timezone.orEmpty() ?: Constants.empty,
        this?.timezone_offset?.orZero() ?: Constants.zero,
        this?.alerts?.map { obj -> obj.fromDomain() }?.toList() ?: emptyList()

    )
}

fun Temp?.fromDomain(): TempResponse {
    return TempResponse(
        this?.day?.orZero() ?: Constants.zero.toDouble(),
        this?.eve?.orZero() ?: Constants.zero.toDouble(),
        this?.max?.orZero() ?: Constants.zero.toDouble(),
        this?.min?.orZero() ?: Constants.zero.toDouble(),
        this?.morn?.orZero() ?: Constants.zero.toDouble(),
        this?.night?.orZero() ?: Constants.zero.toDouble(),
    )
}

fun Rain?.fromDomain(): RainResponse {
    return RainResponse(this?.`1h`?.orZero() ?: Constants.zero.toDouble())
}

fun Weather?.fromDomain(): WeatherResponse {
    return WeatherResponse(
        this?.description.orEmpty() ?: Constants.empty,
        this?.icon.orEmpty(),
        this?.id?.orZero() ?: Constants.zero,
        this?.main.orEmpty()
    )
}

fun Current?.fromDomain(): CurrentResponse {
    return CurrentResponse(
        this?.clouds?.orZero() ?: Constants.zero,
        this?.dew_point?.orZero() ?: Constants.zero.toDouble(),
        this?.dt?.orZero() ?: Constants.zero,
        this?.feels_like?.orZero() ?: Constants.zero.toDouble(),
        this?.humidity?.orZero() ?: Constants.zero,
        this?.pressure?.orZero() ?: Constants.zero,
        this?.sunrise?.orZero() ?: Constants.zero,
        this?.sunset?.orZero() ?: Constants.zero,
        this?.temp?.orZero() ?: Constants.zero.toDouble(),
        this?.uvi?.orZero() ?: Constants.zero.toDouble(),
        this?.visibility?.orZero() ?: Constants.zero,
        this?.weather?.map { weatherList -> weatherList.fromDomain() }?.toList()
            ?: emptyList<WeatherResponse>(),
        this?.wind_deg?.orZero() ?: Constants.zero,
        this?.wind_speed?.orZero() ?: Constants.zero.toDouble()
    )
}

fun FeelsLike?.fromDomain(): FeelsLikeResponse {
    return FeelsLikeResponse(
        this?.day?.orZero() ?: Constants.zero.toDouble(),
        this?.eve?.orZero() ?: Constants.zero.toDouble(),
        this?.morn?.orZero() ?: Constants.zero.toDouble(),
        this?.night?.orZero() ?: Constants.zero.toDouble()
    )
}

fun Daily?.fromDomain(): DailyResponse {
    return DailyResponse(
        this?.clouds?.orZero() ?: Constants.zero,
        this?.dew_point?.orZero() ?: Constants.zero.toDouble(),
        this?.dt?.orZero() ?: Constants.zero,
        this?.feels_like.fromDomain() ?: FeelsLikeResponse(0.0, 0.0, 0.0, 0.0),
        this?.humidity?.orZero() ?: Constants.zero,
        this?.moon_phase?.orZero() ?: Constants.zero.toDouble(),
        this?.moonrise?.orZero() ?: Constants.zero,
        this?.moonset?.orZero() ?: Constants.zero,
        this?.pop?.orZero() ?: Constants.zero.toDouble(),
        this?.pressure?.orZero() ?: Constants.zero,
        this?.rain?.orZero() ?: Constants.zero.toDouble(),
        this?.sunrise?.orZero() ?: Constants.zero,
        this?.sunset?.orZero() ?: Constants.zero,
        this?.temp.fromDomain(),
        this?.uvi?.orZero() ?: Constants.zero.toDouble(),
        this?.weather?.map { weatherList -> weatherList.fromDomain() }?.toList()
            ?: emptyList<WeatherResponse>(),
        this?.wind_deg?.orZero() ?: Constants.zero,
        this?.wind_speed?.orZero() ?: Constants.zero.toDouble(),
        this?.wind_speed?.orZero() ?: Constants.zero.toDouble()
    )
}

fun Hourly?.fromDomain(): HourlyResponse {
    return HourlyResponse(
        this?.clouds?.orZero() ?: Constants.zero,
        this?.dew_point?.orZero() ?: Constants.zero.toDouble(),
        this?.dt?.orZero() ?: Constants.zero,
        this?.feels_like?.orZero() ?: Constants.zero.toDouble(),
        this?.humidity?.orZero() ?: Constants.zero,
        this?.pop?.orZero() ?: Constants.zero.toDouble(),
        this?.pressure?.orZero() ?: Constants.zero,
        this?.rain.fromDomain(),
        this?.temp?.orZero() ?: Constants.zero.toDouble(),
        this?.uvi?.orZero() ?: Constants.zero.toDouble(),
        this?.visibility?.orZero() ?: Constants.zero,
        this?.weather?.map { weatherList -> weatherList.fromDomain() }?.toList()
            ?: emptyList<WeatherResponse>(),
        this?.wind_deg?.orZero() ?: Constants.zero,
        this?.wind_gust?.orZero() ?: Constants.zero.toDouble(),
        this?.wind_speed?.orZero() ?: Constants.zero.toDouble()
    )
}

fun Minutely?.fromDomain(): MinutelyResponse {
    return MinutelyResponse(
        this?.dt?.orZero() ?: Constants.zero, this?.precipitation?.orZero() ?: Constants.zero
    )
}

fun Alert?.fromDomain(): AlertResponse {
    return AlertResponse(
        this?.description.orEmpty(),
        this?.end?.orZero() ?: Constants.zero,
        this?.event.orEmpty(),
        this?.sender_name.orEmpty(),
        this?.start?.orZero() ?: Constants.zero,
        this?.tags?.map { tag -> tag.orEmpty() }?.toList() ?: emptyList()
    )
}

fun UserAlertsEntity?.fromDomain(): UserAlert {
    return UserAlert(
        this?.id?.orZero() ?: Constants.zero,
        this?.startDate?.orZero() ?: Constants.zero.toLong(),
        this?.endDate?.orZero() ?: Constants.zero.toLong(),
        this?.type.orEmpty()
    )
}