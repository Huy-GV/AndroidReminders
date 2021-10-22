package com.reminders.reminders

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.reminders.AppViewModel
import com.reminders.R
import com.reminders.data.enum.Action
import com.reminders.data.model.Reminder



class ReminderAdapter(
    private val viewModel: AppViewModel,
    private val navController: NavController,
    private val updateReminderLabel: String
) : RecyclerView.Adapter<ReminderAdapter.TopicViewHolder>() {

    inner class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkButton: RadioButton = itemView.findViewById(R.id.reminder_check)
        val content: TextView = itemView.findViewById(R.id.reminder_content)
        val card: MaterialCardView = itemView.findViewById(R.id.reminder_card)
        val deadline: TextView = itemView.findViewById(R.id.reminder_deadline)
        val priority: TextView = itemView.findViewById(R.id.reminder_priority)
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
                navController
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
                deadline.text = reminder.deadline!!.format(viewModel.dateFormatter)
            }

            priority.text = reminder.priority.toString()
        }
    }

    override fun getItemCount(): Int {
        return reminders.size
    }


}