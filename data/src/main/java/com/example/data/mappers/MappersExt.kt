package com.example.data.mappers

import com.example.data.models.*
import com.example.data.utils.Constants
import com.example.data.utils.orZero
import com.example.domain.entities.*

fun OneCallResponse?.toDomain(): OneCall {
    return OneCall(
        this?.current.toDomain(),
        this?.daily?.map { obj -> obj.toDomain() }?.toList() ?: emptyList<Daily>(),
        this?.hourly?.map { obj -> obj.toDomain() }?.toList() ?: emptyList(),
        this?.lat?.orZero() ?: Constants.zero.toDouble(),
        this?.lon?.orZero() ?: Constants.zero.toDouble(),
        this?.minutely?.map { obj -> obj.toDomain() }?.toList() ?: emptyList(),
        this?.timezone.orEmpty() ?: Constants.empty,
        this?.timezone_offset?.orZero() ?: Constants.zero,
        this?.alerts?.map { obj->obj.toDomain()}?.toList()?: emptyList()

    )
}

fun TempResponse?.toDomain(): Temp {
    return Temp(
        this?.day?.orZero() ?: Constants.zero.toDouble(),
        this?.eve?.orZero() ?: Constants.zero.toDouble(),
        this?.max?.orZero() ?: Constants.zero.toDouble(),
        this?.min?.orZero() ?: Constants.zero.toDouble(),
        this?.morn?.orZero() ?: Constants.zero.toDouble(),
        this?.night?.orZero() ?: Constants.zero.toDouble(),
    )
}

fun RainResponse?.toDomain(): Rain {
    return Rain(this?.`1h`?.orZero() ?: Constants.zero.toDouble())
}

fun WeatherResponse?.toDomain(): Weather {
    return Weather(
        this?.description.orEmpty() ?: Constants.empty,
        this?.icon.orEmpty(),
        this?.id?.orZero() ?: Constants.zero,
        this?.main.orEmpty()
    )
}

fun CurrentResponse?.toDomain(): Current {
    return Current(
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
        this?.weather?.map { weatherList -> weatherList.toDomain() }?.toList()
            ?: emptyList<Weather>(),
        this?.wind_deg?.orZero() ?: Constants.zero,
        this?.wind_speed?.orZero() ?: Constants.zero.toDouble()
    )
}

fun FeelsLikeResponse?.toDomain(): FeelsLike {
    return FeelsLike(
        this?.day?.orZero() ?: Constants.zero.toDouble(),
        this?.eve?.orZero() ?: Constants.zero.toDouble(),
        this?.morn?.orZero() ?: Constants.zero.toDouble(),
        this?.night?.orZero() ?: Constants.zero.toDouble()
    )
}

fun DailyResponse?.toDomain(): Daily {
    return Daily(
        this?.clouds?.orZero() ?: Constants.zero,
        this?.dew_point?.orZero() ?: Constants.zero.toDouble(),
        this?.dt?.orZero() ?: Constants.zero,
        this?.feels_like.toDomain() ?: FeelsLike(0.0, 0.0, 0.0, 0.0),
        this?.humidity?.orZero() ?: Constants.zero,
        this?.moon_phase?.orZero() ?: Constants.zero.toDouble(),
        this?.moonrise?.orZero() ?: Constants.zero,
        this?.moonset?.orZero() ?: Constants.zero,
        this?.pop?.orZero() ?: Constants.zero.toDouble(),
        this?.pressure?.orZero() ?: Constants.zero,
        this?.rain?.orZero() ?: Constants.zero.toDouble(),
        this?.sunrise?.orZero() ?: Constants.zero,
        this?.sunset?.orZero() ?: Constants.zero,
        this?.temp.toDomain(),
        this?.uvi?.orZero() ?: Constants.zero.toDouble(),
        this?.weather?.map { weatherList -> weatherList.toDomain() }?.toList()
            ?: emptyList<Weather>(),
        this?.wind_deg?.orZero() ?: Constants.zero,
        this?.wind_speed?.orZero() ?: Constants.zero.toDouble(),
        this?.wind_speed?.orZero() ?: Constants.zero.toDouble()
    )
}

fun HourlyResponse?.toDomain(): Hourly {
    return Hourly(
        this?.clouds?.orZero() ?: Constants.zero,
        this?.dew_point?.orZero() ?: Constants.zero.toDouble(),
        this?.dt?.orZero() ?: Constants.zero,
        this?.feels_like?.orZero() ?: Constants.zero.toDouble(),
        this?.humidity?.orZero() ?: Constants.zero,
        this?.pop?.orZero() ?: Constants.zero.toDouble(),
        this?.pressure?.orZero() ?: Constants.zero,
        this?.rain.toDomain(),
        this?.temp?.orZero() ?: Constants.zero.toDouble(),
        this?.uvi?.orZero() ?: Constants.zero.toDouble(),
        this?.visibility?.orZero() ?: Constants.zero,
        this?.weather?.map { weatherList -> weatherList.toDomain() }?.toList()
            ?: emptyList<Weather>(),
        this?.wind_deg?.orZero() ?: Constants.zero,
        this?.wind_gust?.orZero() ?: Constants.zero.toDouble(),
        this?.wind_speed?.orZero() ?: Constants.zero.toDouble()
    )
}

fun MinutelyResponse?.toDomain(): Minutely {
    return Minutely(
        this?.dt?.orZero() ?: Constants.zero, this?.precipitation?.orZero() ?: Constants.zero
    )
}
fun AlertResponse?.toDomain(): Alert {
    return Alert(
        this?.description.orEmpty() ,
        this?.end?.orZero()?:Constants.zero,
        this?.event.orEmpty(),
        this?.sender_name.orEmpty(),
        this?.start?.orZero()?:Constants.zero,
        this?.tags?.map {tag->tag.orEmpty() }?.toList()?: emptyList()
    )
}





