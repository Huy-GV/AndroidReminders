package com.reminders.reminders

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.reminders.AppViewModel
import com.reminders.R
import com.reminders.data.enum.Action
import com.reminders.data.model.Reminder
import java.time.LocalDate
import java.util.Timer
import kotlin.concurrent.schedule

class ReminderAdapter(
    private val viewModel: AppViewModel,
    private val updateReminderLabel: String
) : RecyclerView.Adapter<ReminderAdapter.TopicViewHolder>() {

    inner class TopicViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val checkButton: RadioButton = view.findViewById(R.id.reminder_check)
        val reminderContentView: TextView = view.findViewById(R.id.reminder_content)
        val reminderCardView: MaterialCardView = view.findViewById(R.id.reminder_card)
        val reminderDeadlineView: TextView = view.findViewById(R.id.reminder_deadline)
        val reminderPriorityLevelView: TextView = view.findViewById(R.id.reminder_priority)
    }

    private val reminders = mutableListOf<Reminder>()

    fun updateData(newData: List<Reminder>) {
        reminders.clear()
        reminders.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val inflatedLayout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.reminder_view_holder, parent, false)

        return TopicViewHolder(inflatedLayout)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.apply {
            reminderContentView.text = reminder.content
            checkButton.isChecked = false
            checkButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // display a checked radio button for 1.5s to confirm the user's action
                    Timer("FinishReminder", false).schedule(R.integer.reminder_removal_delay_time.toLong()) {
                        viewModel.deleteReminder(reminder)
                    }
                }
            }

            reminderCardView.setOnClickListener {
                view.findNavController()
                    .navigate(ReadReminderFragmentDirections
                    .actionReadReminderFragmentToCreateUpdateReminderFragment(
                        reminder.topicId,
                        Action.UPDATE,
                        reminder,
                        updateReminderLabel
                    ))
            }

            if (reminder.deadline == null ) {
                reminderDeadlineView.text =  ""
            } else {
                reminderDeadlineView.text = view.resources.getString(
                    R.string.deadline_info,
                    reminder.deadline!!.format(viewModel.dateFormatter)
                )
                if (LocalDate.now().isAfter(reminder.deadline)) {
                    reminderDeadlineView.setTextColor(ContextCompat.getColor(view.context, R.color.red))
                }
            }

            val priorities = view.resources.getStringArray(R.array.priorities)
            reminderPriorityLevelView.text = view.resources.getString(
                R.string.priority_info,
                priorities[reminder.priority]
            )
            setPriorityColor(view, reminderPriorityLevelView, reminder.priority, priorities.lastIndex)
        }
    }

    private fun setPriorityColor(view: View, priorityView: TextView, priority: Int, maxPriority: Int) {
        when (priority) {
            maxPriority -> priorityView.setTextColor(ContextCompat.getColor(view.context, R.color.red))
            maxPriority - 1 -> priorityView.setTextColor(ContextCompat.getColor(view.context, R.color.orange))
        }
    }

    override fun getItemCount(): Int {
        return reminders.size
    }
}