package com.example.weather_forcast_app.utils

import android.content.Context
import android.content.SharedPreferences

class MySharedPref private constructor(var context: Context) {
    companion object {
        private const val preferenceFile = "SettingsPref"
        private var instance: MySharedPref? = null
        fun getInstance(context: Context): MySharedPref {
            return instance ?: MySharedPref(context)
        }
    }

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()
    fun setValue(key: String, value: Any) {
        when (value) {
            is Int -> {
                editor.putInt(key, value)
            }
            is String -> {
                editor.putString(key, value)
            }
            is Float -> {
                editor.putFloat(key, value)
            }
            is Long -> {
                editor.putLong(key, value)
            }
            is Boolean -> {
                editor.putBoolean(key, value)
            }
        }
        editor.apply()
    }

    fun getIntValue(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun getStringValue(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue).toString()
    }

    fun getFloatValue(key: String, defaultValue: Float = 0f): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    fun getLongValue(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    fun getBooleanValue(keyFlag: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(keyFlag, defaultValue)
    }

    fun removeKey(key: String) {
        editor.remove(key)
        editor.apply()
    }

    fun clear() {
        editor.clear().apply()
    }

}