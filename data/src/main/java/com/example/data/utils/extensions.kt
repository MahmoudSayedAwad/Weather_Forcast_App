package com.example.data.utils

fun Double?.orZero():Double{
    return this ?: Constants.zero.toDouble()
}
fun Int?.orZero():Int{
    return this ?: Constants.zero
}