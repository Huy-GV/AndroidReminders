package com.reminders.misc


import android.app.Dialog
import android.os.Build

import android.os.Bundle
import android.text.Selection.setSelection
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.DatePicker
import androidx.annotation.RequiresApi

import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.reminders.AppViewModel
import com.reminders.R
import java.time.LocalDate
import java.util.*

class DatePickerFragment(private val viewModel: AppViewModel) : DialogFragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var date: Int = 0
        var month: Int = 0
        var year: Int = 0
        val view =  inflater.inflate(R.layout.fragment_date_picker, container, false)
        val datePicker = view.findViewById<DatePicker>(R.id.calendarView)
        datePicker.setOnDateChangedListener { _, pickedYear, monthOfYear, dayOfMonth ->
            date = dayOfMonth
            month = monthOfYear
            year = pickedYear
        }

        view.findViewById<Button>(R.id.confirm_date_button).setOnClickListener {
            if (date != 0 && month != 0 && year != 0) {
                viewModel.dateString = "${date}/${month}/${year}"
            }
            Log.d("huy", viewModel.dateString)
            dismiss()
        }

        view.findViewById<Button>(R.id.cancel_button).setOnClickListener { dismiss() }


        return view
    }


    companion object {
        val TAG = "DatePickerDialog"
    }

}