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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.reminders.AppViewModel
import com.reminders.R
import com.reminders.application.MyApplication
import com.reminders.data.model.Topic
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class CreateReminderFragment : Fragment() {
    //TODO: create a superclass fragment?
    private var topicId: Int? = null

    private val appViewModel: AppViewModel by activityViewModels {
        val database = (activity?.application as MyApplication).database
        AppViewModel.Factory(
            database.reminderDao(),
            database.topicDao()
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            topicId = it.getInt(ReadReminderFragment.TOPIC_ID)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_create_reminder, container, false)
        val contentField = view.findViewById<TextInputEditText>(R.id.reminder_content_field)
        val deadlineField = view.findViewById<TextInputEditText>(R.id.reminder_deadline_field)
        val priorityField = view.findViewById<TextInputEditText>(R.id.reminder_priority_field)
        view.findViewById<Button>(R.id.create_reminder_button).setOnClickListener {

            appViewModel.createReminder(
                content = contentField.text.toString(),
                deadline = Date(deadlineField.text.toString()),
                priority = priorityField.text.toString().toInt(),
                topicId = topicId!!
            )
            findNavController().navigateUp()
        }

        return view
    }

}