package com.example.weather_forcast_app.ui.alerts

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.example.domain.entities.UserAlertsEntity
import com.example.domain.utils.ResultResponse
import com.example.weather_forcast_app.R
import com.example.weather_forcast_app.databinding.FragmentAlertsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class AlertsFragment : Fragment(), OnDeleteAlert {
    lateinit var binding: FragmentAlertsBinding

    private val viewModel: AlertsViewModel by viewModels()
    lateinit var alertsAdapter: AlertsAdapter
    private lateinit var dialog: Dialog
    private lateinit var startDateid: EditText
    private lateinit var saveAlertsData: Button
    private lateinit var endDateid: EditText
    private lateinit var alertOptions: RadioGroup
    private var startDate: Calendar = Calendar.getInstance()
    private var endDate: Calendar = Calendar.getInstance()
    private var option = "alarm"
    private var startLongDate: Long = 0
    private var endLongDate: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlertsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.time_alert)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        startDateid = dialog.findViewById(R.id.startDateid)
        endDateid = dialog.findViewById(R.id.endDateid)
        alertOptions = dialog.findViewById(R.id.alertOptions)
        saveAlertsData = dialog.findViewById(R.id.saveAlertsData)
        binding.addAlertsFloatingActionButton.setOnClickListener {
            dialog.show()
        }
        startDateid.setOnClickListener {
            showDateTimePicker("startDateEditText")
        }
        endDateid.setOnClickListener {
            showDateTimePicker("endDateEditText")

        }
        alertOptions.setOnCheckedChangeListener { _, checkedId ->
            val radioButton: RadioButton = dialog.findViewById(checkedId)
            option = radioButton.getText().toString()
            if (option == getString(R.string.alarm)) {
                option = "alarm"
            } else {
                option = "notification"
            }
        }
        saveAlertsData.setOnClickListener {
            if (startDateid.text.toString().isEmpty()) {
                startDateid.error = "Field is required"
            } else if (endDateid.text.toString().isEmpty()) {
                endDateid.error = "Field is required"
            } else {
                val userAlerts = UserAlertsEntity(
                    id = null, startDate = startLongDate, endDate = endLongDate, type = option
                )
                checkPermissionOfOverlay()
                viewModel.addUserAlert(userAlerts)

                startDateid.setText("")
                endDateid.setText("")
                dialog.cancel()
            }
        }

        viewModel.getAlertsList()
        lifecycleScope.launch {
            viewModel.alertList.collect { result ->
                when (result) {
                    is ResultResponse.OnSuccess -> {
                        alertsAdapter = AlertsAdapter(
                            requireContext(), viewModel.getLanguage(), this@AlertsFragment
                        )
                        alertsAdapter.submitList(result.data)
                        binding.alertsRecyclerView.apply {
                            adapter = alertsAdapter
                            layoutManager = LinearLayoutManager(context).apply {
                                orientation = RecyclerView.VERTICAL
                            }
                        }
                    }
                    else -> {}
                }
            }
        }
        lifecycleScope.launch {

            viewModel.id.collect {
                when (it) {
                    is ResultResponse.OnSuccess -> {
                        setPeriodWorkManger(it.data)
                    }
                    else -> {}
                }
            }
        }
    }

    override fun delete(alert: UserAlertsEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle(requireView().resources.getString(R.string.warning))
            .setMessage(getString(R.string.delete_place)).setPositiveButton(R.string.ok) { _, _ ->
                lifecycleScope.launch {
                    viewModel.deleteFromUserAlert(alert)
                    //                   WorkManager.getInstance().cancelAllWorkByTag("${alert.id}")
                    println(alert)
                    viewModel.getAlertsList()
                }
            }.setNegativeButton(R.string.cancel) { _, _ -> }.setIcon(R.drawable.ic_warning_24)
            .show()

    }

    private fun setPeriodWorkManger(id: Long) {

        val data = Data.Builder()
        data.putLong("id", id)

        val constraints = Constraints.Builder().setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED).build()
        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            AlertPeriodicWorkManger::class.java, 24, TimeUnit.HOURS
        ).setConstraints(constraints).setInputData(data.build()).build()
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "$id", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest
        )
    }

    private fun showDateTimePicker(label: String) {
        val currentDate = Calendar.getInstance()
        var date: Calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(), { _, year, monthOfYear, dayOfMonth ->
                date.set(year, monthOfYear, dayOfMonth)
                TimePickerDialog(
                    context, { _, hourOfDay, minute ->
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        date.set(Calendar.MINUTE, minute)
                        if (label == "startDateEditText") {
                            startDate = date
                            startLongDate = updateLabel(startDate, startDateid)
                        } else {
                            endDate = date
                            endLongDate = updateLabel(endDate, endDateid)
                        }

                    }, currentDate[Calendar.HOUR_OF_DAY], currentDate[Calendar.MINUTE], false
                ).show()
            }, currentDate[Calendar.YEAR], currentDate[Calendar.MONTH], currentDate[Calendar.DATE]
        )
        datePickerDialog.datePicker.minDate = currentDate.timeInMillis;
        datePickerDialog.show();
    }

    private fun updateLabel(calendar: Calendar, editText: EditText): Long {
        val myFormat = "HH:mm a\ndd/MM/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale(viewModel.getLanguage()))
        val milliseconds: Long = calendar.timeInMillis
        editText.setText(dateFormat.format(calendar.time))
        return milliseconds
    }


    private fun checkPermissionOfOverlay() {
        if (!Settings.canDrawOverlays(requireContext())) {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Display on top")
                .setMessage("You Should let us to draw on top")
                .setPositiveButton("Okay") { dialog: DialogInterface, _: Int ->
                    // Navigate to Manage Overlay settings in device
                    val intent = Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + requireContext().applicationContext.packageName)
                    )
                    startActivityForResult(intent, 1)
                    dialog.dismiss()
                }.setNegativeButton("No") { dialog: DialogInterface, _: Int ->
                    dialog.dismiss()
                }.show()
        }
    }
}