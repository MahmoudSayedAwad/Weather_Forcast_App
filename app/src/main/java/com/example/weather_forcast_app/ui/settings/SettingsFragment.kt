package com.example.weather_forcast_app.ui.settings

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.weather_forcast_app.R
import com.example.weather_forcast_app.databinding.FragmentSettingsBinding
import com.example.weather_forcast_app.utils.Constants
import com.example.weather_forcast_app.utils.Constants.APPLICATION_LANGUAGE_AR
import com.example.weather_forcast_app.utils.Constants.APPLICATION_LANGUAGE_EN
import com.example.weather_forcast_app.utils.Constants.LOCATION_METHOD_GPS
import com.example.weather_forcast_app.utils.Constants.MEASUREMENT_UNIT_IMPERIAL
import com.example.weather_forcast_app.utils.Constants.MEASUREMENT_UNIT_METRIC
import com.example.weather_forcast_app.utils.Constants.MEASUREMENT_UNIT_STANDARD
import com.example.weather_forcast_app.utils.Constants.NOTIFICATION_Type_ALERT
import com.example.weather_forcast_app.utils.Constants.NOTIFICATION_Type_NOTI
import com.example.weather_forcast_app.utils.Constants.WIND_SPEED_UNIT_M_P_H
import com.example.weather_forcast_app.utils.Constants.WIND_SPEED_UNIT_M_P_S
import com.example.weather_forcast_app.utils.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)

    }

    fun init(view: View) {
        lateinit var radioButton: RadioButton
        binding.location.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.GPSRbtn -> {
                    //  viewModel.setLocationMethod(LOCATION_METHOD_GPS)
                }
                R.id.mapRBtn -> {
                    //  viewModel.setLocationMethod(LOCATION_METHOD_MAP)
                }
            }


        }
        binding.GPSRbtn.setOnClickListener {
            viewModel.setLocationMethod(LOCATION_METHOD_GPS)

        }
        binding.mapRBtn.setOnClickListener {
            val action =
                SettingsFragmentDirections.actionNavigationSettingsToMapFragment(Constants.COMING_FROM_SETTING)
            Navigation.findNavController(requireView()).navigate(action)

        }
        binding.windSpeed.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.MeterSec -> {
                    viewModel.setWindSpeedUnit(WIND_SPEED_UNIT_M_P_S)
                }
                R.id.MileHour -> {
                    viewModel.setWindSpeedUnit(WIND_SPEED_UNIT_M_P_H)
                }
            }
        }
        binding.temperature.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.Celsius -> {
                    viewModel.setCurrentTempMeasurementUnit(MEASUREMENT_UNIT_METRIC)
                }
                R.id.Fahrenheit -> {
                    viewModel.setCurrentTempMeasurementUnit(MEASUREMENT_UNIT_IMPERIAL)
                }
                R.id.Kelvin -> {
                    viewModel.setWindSpeedUnit(MEASUREMENT_UNIT_STANDARD)
                }
            }

        }
        binding.english.setOnClickListener{
           // LocaleHelper.setAppLocale(requireContext(), APPLICATION_LANGUAGE_EN)
            requireActivity().recreate()
        }
        binding.arabic.setOnClickListener{
            requireActivity().recreate()
        }
        binding.Language.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.english -> {
                    viewModel.setLanguage(APPLICATION_LANGUAGE_EN)
                    LocaleHelper.setAppLocale(requireContext(), APPLICATION_LANGUAGE_EN)

                }
                R.id.arabic -> {
                    viewModel.setLanguage(APPLICATION_LANGUAGE_AR)
                    LocaleHelper.setAppLocale(requireContext(), APPLICATION_LANGUAGE_AR)

                }
            }

        }
        binding.notification.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.on -> {
                    viewModel.setNotificationChecked(true)
                }
                R.id.off -> {
                    viewModel.setNotificationChecked(false)
                }
            }
        }
        binding.alert.setOnClickListener{

        }
        binding.alertType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.notif -> {
                    viewModel.setNotificationType(NOTIFICATION_Type_NOTI)
                }
                R.id.alert -> {
                    viewModel.setNotificationType(NOTIFICATION_Type_ALERT)

                }
            }
        }
        viewModel.registerOnSharedPrefChanged()
        viewModel.getLocationMethod()
        viewModel.getLanguage()
        viewModel.getWindSpeedUnit()
        viewModel.getCurrentTempMeasurementUnit()
        viewModel.getNotificationChecked()
        viewModel.getNotificationType()
        viewModel.changedSuccessfully.observe(viewLifecycleOwner) {
            Toast.makeText(view.context, it, Toast.LENGTH_LONG).show()
        }

        viewModel.locationMethod.observe(viewLifecycleOwner) {
            if (it == LOCATION_METHOD_GPS) {
                binding.GPSRbtn.isChecked = true
            } else {
                binding.mapRBtn.isChecked = true
            }
        }
        viewModel.language.observe(viewLifecycleOwner) {
            if (it == APPLICATION_LANGUAGE_EN) {
                binding.english.isChecked = true
            } else {
                binding.arabic.isChecked = true
            }
        }
        viewModel.windSpeedUnit.observe(viewLifecycleOwner) {
            if (it == WIND_SPEED_UNIT_M_P_S) {
                binding.MeterSec.isChecked = true
            } else {
                binding.MileHour.isChecked = true
            }
        }
        viewModel.currentTempMeasurementUnit.observe(viewLifecycleOwner) {
            when (it) {
                MEASUREMENT_UNIT_METRIC -> {
                    binding.Celsius.isChecked = true
                }
                MEASUREMENT_UNIT_STANDARD -> {
                    binding.Kelvin.isChecked = true
                }
                else -> {
                    binding.Fahrenheit.isChecked = true
                }
            }
        }
        viewModel.notificationChecked.observe(viewLifecycleOwner) {
            if (it) {
                binding.on.isChecked = true
            } else {
                binding.off.isChecked = true
            }
        }
        viewModel.notificationType.observe(viewLifecycleOwner) {
            if (it == NOTIFICATION_Type_NOTI) {
                binding.notif.isChecked = true
            } else {
                binding.alert.isChecked = true
            }
        }
    }


}