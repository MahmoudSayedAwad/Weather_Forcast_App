package com.example.weather_forcast_app.ui.home

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.utils.ResultResponse
import com.example.weather_forcast_app.R
import com.example.weather_forcast_app.utils.Constants.IMG_URL
import com.example.weather_forcast_app.utils.Constants.LOCATION_METHOD_MAP
import com.example.weather_forcast_app.utils.Constants.MEASUREMENT_UNIT_IMPERIAL
import com.example.weather_forcast_app.utils.Constants.MEASUREMENT_UNIT_METRIC
import com.example.weather_forcast_app.utils.Constants.MEASUREMENT_UNIT_STANDARD
import com.example.weather_forcast_app.utils.Constants.WIND_SPEED_UNIT_M_P_H
import com.example.weather_forcast_app.utils.Constants.WIND_SPEED_UNIT_M_P_S
import com.example.weather_forcast_app.utils.getDateTime
import com.example.weather_forcast_app.utils.permissionId
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var progressBar: ProgressBar
    private lateinit var city: TextView
    private lateinit var date: TextView
    private lateinit var weatherDescription: TextView
    private lateinit var tempTextView: TextView
    private lateinit var tempTypeTextView: TextView //°C مثلا
    private lateinit var tempIcon: ImageView
    private lateinit var hourlyWeatherRc: RecyclerView
    private lateinit var dailyWeatherRc: RecyclerView
    private lateinit var cardView: CardView
    private lateinit var pressureTxt: TextView
    private lateinit var humidityTxt: TextView
    private lateinit var windTxt: TextView
    private lateinit var cloudTxt: TextView
    private lateinit var ultraVioletTxt: TextView
    private lateinit var visibilityTxt: TextView
    private lateinit var hourlyWeathersAdapter: HourlyAdapter
    private lateinit var daysWeathersAdapter: DailyAdapter
    private lateinit var showWeatherData: ConstraintLayout
    private lateinit var windSpeedDisc: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  requestPermissions()
        init(view)
        if (viewModel.getLocationMethod() == LOCATION_METHOD_MAP) {
            viewModel.getCurrentWeather(
                viewModel.getLatitude(),
                viewModel.getLongitude(),
                viewModel.getCurrentTempMeasurementUnit(),
                viewModel.getLanguage(),
                "67ca8d4acae59d540ea421e817caf1bb"
            )
            viewModel.getCityName(
                viewModel.getLatitude(),
                viewModel.getLongitude(), requireContext()
            )

        } else {
            viewModel.getLocation(requireContext(), requireActivity())
                .observe(viewLifecycleOwner, Observer {
                    println(it.latitude)
                    viewModel.setLatitude(it.latitude.toFloat())
                    viewModel.setLongitude(it.longitude.toFloat())
                    viewModel.getCurrentWeather(
                        it.latitude,
                        it.longitude,
                        viewModel.getCurrentTempMeasurementUnit(),
                        viewModel.getLanguage(),
                        "67ca8d4acae59d540ea421e817caf1bb"
                    )
                    viewModel.getCityName(it.latitude, it.longitude, requireContext())

                })
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            }
        }
    }

    private fun init(view: View) {
        city = view.findViewById(R.id.tvCity)
        date = view.findViewById(R.id.tvDateTime)
        weatherDescription = view.findViewById(R.id.tvHomeWeatherDescription)
        tempTextView = view.findViewById(R.id.tvTemp)
        tempTypeTextView = view.findViewById(R.id.tvHomeTempDisc) //°C مثلا
        tempIcon = view.findViewById(R.id.ivWeatherIcon)
        hourlyWeatherRc = view.findViewById(R.id.recyclerViewHourly)
        dailyWeatherRc = view.findViewById(R.id.recyclerViewDaily)
        progressBar = view.findViewById(R.id.HomeprogressBar)
        cardView = view.findViewById(R.id.HomecardView)
        pressureTxt = view.findViewById(R.id.tvPressure)
        humidityTxt = view.findViewById(R.id.tvHumidity)
        windTxt = view.findViewById(R.id.txtViewWindSpeed)
        cloudTxt = view.findViewById(R.id.tvClouds)
        ultraVioletTxt = view.findViewById(R.id.tvUVI)
        visibilityTxt = view.findViewById(R.id.tvVisibility)
        hourlyWeathersAdapter = HourlyAdapter(emptyList(), view.context, viewModel.getLanguage())
        daysWeathersAdapter = DailyAdapter(view.context, emptyList(), viewModel.getLanguage())
        showWeatherData = view.findViewById(R.id.showWeatherData)
        windSpeedDisc = view.findViewById(R.id.txtViewWindSpeedDiscrimination)
        var layoutManager = LinearLayoutManager(view.context)
        hourlyWeatherRc.apply {
            setHasFixedSize(true)
            layoutManager.orientation = RecyclerView.HORIZONTAL
            this.layoutManager = layoutManager
            adapter = hourlyWeathersAdapter
        }

        var layoutManagerDays = LinearLayoutManager(view.context)
        dailyWeatherRc.apply {
            setHasFixedSize(true)
            layoutManagerDays.orientation = RecyclerView.VERTICAL
            this.layoutManager = layoutManagerDays
            adapter = daysWeathersAdapter
        }
        // viewModel.getLocationAndSaveItInSharedPref(requireActivity(),requireContext())
        lifecycleScope.launch {
            viewModel.currentWeather.collect { result ->
                when (result) {
                    is ResultResponse.OnSuccess -> {
                        viewModel.addToDatabase(result.data)
                        showWeatherData.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        val weatherCurrent = result.data.current
                        val weatherDesc = weatherCurrent.weather.get(0)
                        date.text = getDateTime(
                            weatherCurrent.dt, "EEE, d MMM ", viewModel.getLanguage()
                        )

                        viewModel.cityName.observe(viewLifecycleOwner) {
                            city.text = it ?: ""
                        }
                        weatherDescription.text = weatherDesc.description
                        tempTextView.text = weatherCurrent.temp.roundToInt().toString()
                        tempTypeTextView.text =
                            if (viewModel.getCurrentTempMeasurementUnit() == MEASUREMENT_UNIT_STANDARD) {
                                "K"
                            } else if (viewModel.getCurrentTempMeasurementUnit() == MEASUREMENT_UNIT_IMPERIAL) {
                                "F"
                            } else {
                                "C"
                            }

                        Picasso.get().load("${IMG_URL}${weatherDesc.icon}@4x.png").into(tempIcon)
                        pressureTxt.text = weatherCurrent.pressure.toString()
                        humidityTxt.text = weatherCurrent.humidity.toString()
                        cloudTxt.text = weatherCurrent.clouds.toString()
                        visibilityTxt.text = weatherCurrent.clouds.toString()
                        windTxt.text =
                            if ((viewModel.getWindSpeedUnit() == WIND_SPEED_UNIT_M_P_S) && (viewModel.getCurrentTempMeasurementUnit() == MEASUREMENT_UNIT_IMPERIAL)) {
                                (weatherCurrent.wind_speed * 0.44704).roundToInt().toString()
                            } else if (viewModel.getWindSpeedUnit() == WIND_SPEED_UNIT_M_P_H && ((viewModel.getCurrentTempMeasurementUnit() == MEASUREMENT_UNIT_STANDARD) || (viewModel.getCurrentTempMeasurementUnit() == MEASUREMENT_UNIT_METRIC))) {

                                (weatherCurrent.wind_speed * 2.23693629).roundToInt().toString()
                            } else {
                                weatherCurrent.wind_speed.toString()
                            }

                        windSpeedDisc.text =
                            if (viewModel.getWindSpeedUnit() == WIND_SPEED_UNIT_M_P_S) {
                                view.resources.getString(R.string.meter_sec)
                            } else {
                                view.resources.getString(R.string.mile_hour)
                            }
                        ultraVioletTxt.text = weatherCurrent.uvi.toString()
                        daysWeathersAdapter.setDays(result.data.daily)
                        hourlyWeathersAdapter.sethours(result.data.hourly)

                    }
                    is ResultResponse.OnLoading -> {
                        showWeatherData.visibility = View.INVISIBLE
                        progressBar.visibility = View.VISIBLE
                    }
                    is ResultResponse.OnError -> {
                        showWeatherData.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                    }
                    else -> {}
                }
            }
        }
    }


}