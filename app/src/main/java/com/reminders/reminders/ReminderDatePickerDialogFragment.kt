package com.reminders.reminders

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.reminders.AppViewModel
import com.reminders.R
import java.time.LocalDate

class ReminderDatePickerDialogFragment(private val viewModel: AppViewModel) : DialogFragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var date = 0
        var month = 0
        var year = 0
        val view =  inflater.inflate(R.layout.fragment_date_picker, container, false)
        val datePicker = view.findViewById<DatePicker>(R.id.calendarView)

        datePicker.setOnDateChangedListener { _, pickedYear, monthOfYear, dayOfMonth ->
            date = dayOfMonth

            // add 1 because monthOfYear is 0-indexed
            month = monthOfYear + 1
            year = pickedYear
        }

        view.findViewById<Button>(R.id.confirm_date_button).setOnClickListener {
            if (date != 0 && month != 0 && year != 0) {
                viewModel.updateDeadlineString(LocalDate.of(year, month, date))
            }

            dismiss()
        }

        view.findViewById<Button>(R.id.cancel_button).setOnClickListener {
            dismiss()
        }

        return view
    }

    companion object {
        const val TAG = "DatePickerDialog"
    }
}