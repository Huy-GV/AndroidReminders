package com.reminders.reminders

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.reminders.AppViewModel
import com.reminders.R
import com.reminders.application.MyApplication
import com.reminders.data.enum.Action
import com.reminders.data.model.Reminder
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class CreateUpdateReminderFragment : Fragment() {
    //TODO: create a superclass fragment?
    private var topicId: Int? = null
    private lateinit var action: Action
    private var reminder: Reminder? = null

    private lateinit var priorities: Array<String>

    private lateinit var contentField: TextInputEditText
    private lateinit var deadlineField: TextInputEditText
    private lateinit var priorityField: AutoCompleteTextView
    private lateinit var positiveButton: Button

    @RequiresApi(Build.VERSION_CODES.O)
    private val df: DateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy")
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
            topicId = it.getInt(TOPIC_ID)
            action = it.getSerializable(ACTION) as Action
            reminder = it.getParcelable(REMINDER)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_create_update_reminder, container, false)
        contentField = view.findViewById(R.id.reminder_content_field)
        deadlineField = view.findViewById(R.id.reminder_deadline_field)
        priorityField = view.findViewById(R.id.reminder_priority_field)
        positiveButton = view.findViewById(R.id.positive_reminder_button)
        view.findViewById<Button>(R.id.cancel_reminder_button).setOnClickListener {
            findNavController().navigateUp()
        }


        priorities = resources.getStringArray(R.array.priorities)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, priorities)
        priorityField.setAdapter(arrayAdapter)

        when (action ){
            Action.CREATE -> setCreateAction()
            Action.UPDATE -> setUpdateAction(reminder!!)
        }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpdateAction(reminder: Reminder) {
        positiveButton.text = "SAVE"
        contentField.setText(reminder.content)
        deadlineField.setText(reminder.deadline.toString())
        priorityField.setText(priorities[reminder.priority])

        //TODO: priority select no working
        positiveButton.setOnClickListener {
            reminder.apply {
                content = contentField.text.toString()
                deadline = LocalDate.parse(deadlineField.text.toString(), df)
                priority = getPriorityLevel(priorityField.text.toString())
            }

            appViewModel.updateReminder(reminder)
            findNavController().navigateUp()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setCreateAction() {
        positiveButton.text = "CREATE"
        positiveButton.setOnClickListener {

            appViewModel.createReminder(
                content = contentField.text.toString(),
                rawDeadline = deadlineField.text.toString(),
                priority = getPriorityLevel(priorityField.text.toString()),
                topicId = topicId!!
            )
            findNavController().navigateUp()
        }
    }

    private fun getPriorityLevel(priority: String) : Int {
        val priorities = resources.getStringArray(R.array.priorities)
        priorities.forEachIndexed { index, priorityString ->
            if (priorityString == priority) return@getPriorityLevel index;
        }
        return 1
    }

    companion object {
        val TOPIC_ID = "topic_id"
        val REMINDER = "reminder"
        val ACTION = "action_type"
    }
}