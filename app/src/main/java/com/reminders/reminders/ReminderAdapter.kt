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


class ReminderAdapter(
    private val viewModel: AppViewModel,
    private val updateReminderLabel: String
) : RecyclerView.Adapter<ReminderAdapter.TopicViewHolder>() {

    inner class TopicViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val checkButton: RadioButton = view.findViewById(R.id.reminder_check)
        val content: TextView = view.findViewById(R.id.reminder_content)
        val card: MaterialCardView = view.findViewById(R.id.reminder_card)
        val deadline: TextView = view.findViewById(R.id.reminder_deadline)
        val priority: TextView = view.findViewById(R.id.reminder_priority)
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
            content.text = reminder.content
            checkButton.isChecked = false
            checkButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.deleteReminder(reminder)
                }
            }
            card.setOnClickListener {
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
                deadline.text =  ""
            } else {
                deadline.text = view.resources.getString(
                    R.string.deadline_info,
                    reminder.deadline!!.format(viewModel.dateFormatter)
                )
                if (LocalDate.now().isAfter(reminder.deadline)) {
                    deadline.setTextColor(ContextCompat.getColor(view.context, R.color.red))
                }
            }

//            viewModel.setDeadlineString(reminder.deadline)
//            deadline.text = viewModel.deadlineString.value

            val priorities = view.resources.getStringArray(R.array.priorities)
            priority.text = view.resources.getString(
                R.string.priority_info,
                priorities[reminder.priority]
            )
            setPriorityColor(view, priority, reminder.priority, priorities.lastIndex)
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