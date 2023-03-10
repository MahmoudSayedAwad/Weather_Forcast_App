package com.example.weather_forcast_app.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.example.weather_forcast_app.R
import com.example.weather_forcast_app.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        /*val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)*/

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // val textView: TextView = binding.textNotifications
        /* notificationsViewModel.text.observe(viewLifecycleOwner) {
             textView.text = it
         }*/
        return root
        //return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    fun init(view: View) {
        lateinit var radioButton: RadioButton
        binding.location.setOnCheckedChangeListener { radioGroup, checkedId ->
            radioButton = view.findViewById(checkedId)
            var locationText = radioButton.getText().toString()
            Log.e("Tag", "addRadioGroupListener: $locationText")
        }
        binding.windSpeed.setOnCheckedChangeListener { radioGroup, checkedId ->
            radioButton = view.findViewById(checkedId)
            var windSpeedText = radioButton.getText().toString()
            Log.e("Tag", "addRadioGroupListener: $windSpeedText")
        }
        binding.temperature.setOnCheckedChangeListener { _, checkedId ->
            radioButton = view.findViewById(checkedId)
            var temperatureText = radioButton.getText().toString()
            Log.e("Tag", "addRadioGroupListener: $temperatureText")
        }
        binding.Language.setOnCheckedChangeListener { _, checkedId ->
            radioButton = view.findViewById(checkedId)
            var languageText = radioButton.getText().toString()
            Log.e("Tag", "addRadioGroupListener: ${languageText}")
        }
        binding.notification.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.GPSRbtn -> {

                }
                R.id.mapRBtn -> {

                }
            }
        }
    }
}