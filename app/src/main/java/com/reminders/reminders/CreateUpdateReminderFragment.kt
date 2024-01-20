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
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import com.reminders.AppViewModel
import com.reminders.R
import com.reminders.application.MyApplication
import com.reminders.data.enum.Action
import com.reminders.data.model.Reminder

@RequiresApi(Build.VERSION_CODES.O)
class CreateUpdateReminderFragment : Fragment() {

    private val args: CreateUpdateReminderFragmentArgs by navArgs()

    private lateinit var priorities: Array<String>
    private lateinit var contentField: TextInputEditText
    private lateinit var deadlineField: TextInputEditText
    private lateinit var priorityField: AutoCompleteTextView
    private lateinit var positiveButton: Button

    private val viewModel: AppViewModel by activityViewModels {
        val database = (activity?.application as MyApplication).database
        AppViewModel.Factory(
            database.reminderDao(),
            database.topicDao()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_create_update_reminder, container, false)

        view.apply {
            contentField = findViewById(R.id.reminder_content_field)
            deadlineField = findViewById(R.id.reminder_deadline_field)
            priorityField = findViewById(R.id.reminder_priority_field)
            positiveButton = findViewById(R.id.positive_reminder_button)
            findViewById<Button>(R.id.cancel_reminder_button).setOnClickListener {
                findNavController().navigateUp()
            }
        }

        priorities = resources.getStringArray(R.array.priorities)
        priorityField.setAdapter(ArrayAdapter(requireContext(), R.layout.dropdown_item, priorities))
        priorityField.hint = resources.getString(R.string.priority_hint)

        deadlineField.setOnClickListener {
            ReminderDatePickerDialogFragment(viewModel)
                .show(parentFragmentManager, ReminderDatePickerDialogFragment.TAG)
        }

        viewModel.deadlineString.observe(viewLifecycleOwner) {
            deadlineField.setText(it)
        }

        when (args.actionType ){
            Action.CREATE -> setCreateAction()
            Action.UPDATE -> setUpdateAction(args.reminder!!)
        }

        return view
    }

    //the form is prefilled when the user updates a reminder
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpdateAction(reminder: Reminder) {

        positiveButton.text = resources.getString(R.string.save_changes)
        contentField.setText(reminder.content)
        viewModel.updateDeadlineString(reminder.deadline)
        priorityField.hint = priorities[reminder.priority]

        positiveButton.setOnClickListener {
            reminder.apply {
                content = contentField.text.toString()
                deadline = viewModel.parseDeadline()
                priority = getPriorityLevel(priorityField.text.toString())
            }
            viewModel.clearDeadlineString()
            viewModel.updateReminder(reminder)
            findNavController().navigateUp()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setCreateAction() {
        viewModel.clearDeadlineString()
        positiveButton.text = resources.getString(R.string.add_new)
        positiveButton.setOnClickListener {
            viewModel.createReminder(
                content = contentField.text.toString(),
                priority = getPriorityLevel(priorityField.text.toString()),
                topicId = args.topicId
            )
            viewModel.clearDeadlineString()
            findNavController().navigateUp()
        }
    }

    private fun getPriorityLevel(priority: String) : Int {
        val priorities = resources.getStringArray(R.array.priorities)
        priorities.forEachIndexed { index, priorityString ->
            if (priorityString == priority) {
                return@getPriorityLevel index
            }
        }

        return 1
    }
}