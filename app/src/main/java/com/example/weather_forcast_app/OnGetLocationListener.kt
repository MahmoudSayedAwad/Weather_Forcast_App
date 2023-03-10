package com.example.weather_forcast_app

import android.location.Address

interface OnGetLocationListener {
    fun onSuccess(addressList:List<Address>)
}