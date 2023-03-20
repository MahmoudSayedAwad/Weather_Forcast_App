package com.example.weather_forcast_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.utils.ResultResponse
import com.example.weather_forcast_app.databinding.FragmentFavouriteDetailsBinding
import com.example.weather_forcast_app.ui.home.DailyAdapter
import com.example.weather_forcast_app.ui.home.HomeViewModel
import com.example.weather_forcast_app.ui.home.HourlyAdapter
import com.example.weather_forcast_app.ui.map.MapFragmentArgs
import com.example.weather_forcast_app.utils.Constants
import com.example.weather_forcast_app.utils.getDateTime
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class FavouriteDetails : Fragment() {
    lateinit var binding: FragmentFavouriteDetailsBinding
    private val viewModel: HomeViewModel by viewModels()
    lateinit var daysWeathersAdapter: DailyAdapter
    lateinit var hourlyWeathersAdapter: HourlyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(requireView())
        viewModel.getCityName( FavouriteDetailsArgs.fromBundle(requireArguments()).lat.toDouble(),
            FavouriteDetailsArgs.fromBundle(requireArguments()).lon.toDouble(),requireContext())
        viewModel.getCurrentWeather(
            FavouriteDetailsArgs.fromBundle(requireArguments()).lat.toDouble(),
            FavouriteDetailsArgs.fromBundle(requireArguments()).lon.toDouble() ,
            viewModel.getCurrentTempMeasurementUnit(),
            viewModel.getLanguage(),
            "67ca8d4acae59d540ea421e817caf1bb"
        )


    }

    private fun init(view: View) {

        hourlyWeathersAdapter = HourlyAdapter(emptyList(), view.context, viewModel.getLanguage())
        daysWeathersAdapter = DailyAdapter(view.context, emptyList(), viewModel.getLanguage())
        var layoutManager = LinearLayoutManager(view.context)
        binding.recyclerFViewHourly.apply {
            setHasFixedSize(true)
            layoutManager.orientation = RecyclerView.HORIZONTAL
            this.layoutManager = layoutManager
            adapter = hourlyWeathersAdapter
        }

        var layoutManagerDays = LinearLayoutManager(view.context)
        binding.recyclerFViewDaily.apply {
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
                       // viewModel.addToDatabase(result.data)
                        binding.showFWeatherData.visibility = View.VISIBLE
                        binding.HomeFprogressBar.visibility = View.GONE
                        val weatherCurrent = result.data.current
                        val weatherDesc = weatherCurrent.weather.get(0)
                        binding.tvFDateTime.text = getDateTime(
                            weatherCurrent.dt, "EEE, d MMM ", viewModel.getLanguage()
                        )


                        viewModel.cityName.observe(viewLifecycleOwner) {
                            binding.tvFCity.text = it
                        }
                        binding.tvFHomeWeatherDescription.text = weatherDesc.description
                        binding.tvFTemp.text = weatherCurrent.temp.roundToInt().toString()
                        binding.tvFHomeTempDisc.text =
                            if (viewModel.getCurrentTempMeasurementUnit() == Constants.MEASUREMENT_UNIT_STANDARD) {
                                "K"
                            } else if (viewModel.getCurrentTempMeasurementUnit() == Constants.MEASUREMENT_UNIT_IMPERIAL) {
                                "F"
                            } else {
                                "C"
                            }

                        //Picasso.get().load("${Constants.IMG_URL}${weatherDesc.icon}@4x.png")
                        binding.tvFPressure.text = weatherCurrent.pressure.toString()
                        binding.tvFHumidity.text = weatherCurrent.humidity.toString()
                        binding.tvFClouds.text = weatherCurrent.clouds.toString()
                        binding.tvFVisibility.text = weatherCurrent.clouds.toString()
                        binding.txtFViewWindSpeed.text =
                            if ((viewModel.getWindSpeedUnit() == Constants.WIND_SPEED_UNIT_M_P_S) && (viewModel.getCurrentTempMeasurementUnit() == Constants.MEASUREMENT_UNIT_IMPERIAL)) {
                                (weatherCurrent.wind_speed * 0.44704).roundToInt().toString()
                            } else if (viewModel.getWindSpeedUnit() == Constants.WIND_SPEED_UNIT_M_P_H && ((viewModel.getCurrentTempMeasurementUnit() == Constants.MEASUREMENT_UNIT_STANDARD) || (viewModel.getCurrentTempMeasurementUnit() == Constants.MEASUREMENT_UNIT_METRIC))) {

                                (weatherCurrent.wind_speed * 2.23693629).roundToInt().toString()
                            } else {
                                weatherCurrent.wind_speed.toString()
                            }

                        binding.tvFWindSpeed.text =
                            if (viewModel.getWindSpeedUnit() == Constants.WIND_SPEED_UNIT_M_P_S) {
                                view.resources.getString(R.string.meter_sec)
                            } else {
                                view.resources.getString(R.string.mile_hour)
                            }

                        daysWeathersAdapter.setDays(result.data.daily)
                        hourlyWeathersAdapter.sethours(result.data.hourly)
                        binding.tvFUVI.text=weatherCurrent.uvi.toString()

                    }
                    is ResultResponse.OnLoading -> {
                        binding.showFWeatherData.visibility = View.INVISIBLE
                        binding.HomeFprogressBar.visibility = View.VISIBLE
                    }
                    is ResultResponse.OnError -> {
                        binding.showFWeatherData.visibility = View.GONE
                        binding.HomeFprogressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                    }
                    else -> {}
                }
            }
        }
    }

}