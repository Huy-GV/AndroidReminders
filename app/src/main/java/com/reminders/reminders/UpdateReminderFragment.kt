package com.reminders.reminders

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.reminders.AppViewModel
import com.reminders.R
import com.reminders.application.MyApplication
import com.reminders.data.model.Reminder
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

const val REMINDER_PARCEL = "reminder"
class UpdateReminderFragment(
    private val appViewModel: AppViewModel,
    private val reminder: Reminder) : DialogFragment() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update_reminder, container, false)

        val newName = view.findViewById<EditText>(R.id.name_field)
        newName.setText(reminder.content)
        val newDeadline = view.findViewById<EditText>(R.id.deadline_field)
//        val date = LocalDate.parse(reminder.deadline.toString()).toString()
//        newDeadline.setText(date)
        val newPriority = view.findViewById<EditText>(R.id.priority_field)
        newPriority.setText(reminder.priority.toString())

        val confirmButton = view.findViewById<Button>(R.id.update_reminder_button)
        confirmButton.setOnClickListener {
            reminder.apply {
                content = newName.text.toString()
                deadline = LocalDate.parse(newDeadline.text.toString(), DateTimeFormatter.ISO_DATE)
                priority = newPriority.text.toString().toInt()
                reminder.topicId
            }

            appViewModel.updateReminder(reminder)
            dismiss()

        }

        return view
    }

    companion object {
        val TAG = "update-dialog"
    }

}