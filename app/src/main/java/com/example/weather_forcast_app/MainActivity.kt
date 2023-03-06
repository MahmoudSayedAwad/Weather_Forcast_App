package com.example.weather_forcast_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.weather_forcast_app.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var testLat: TextView
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testLat=findViewById(R.id.text_id)
        viewModel.getCurrentWeather(29.9642,32.5056,"metric","en","67ca8d4acae59d540ea421e817caf1bb")
        lifecycleScope.launch {
            viewModel.currentWeather.collect {

            }
        }
    }
}
