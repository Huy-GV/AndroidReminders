package com.reminders.reminders

import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reminders.AppViewModel
import com.reminders.R
import com.reminders.data.model.Reminder
import com.reminders.data.model.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import org.w3c.dom.Text

class ReminderAdapter(
    private val viewModel: AppViewModel
) : RecyclerView.Adapter<ReminderAdapter.TopicViewHolder>() {
    class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkButton = itemView.findViewById<RadioButton>(R.id.reminder_check)
        val content = itemView.findViewById<TextView>(R.id.reminder_content)
        val deadline = itemView.findViewById<TextView>(R.id.reminder_deadline)
        val priority = itemView.findViewById<TextView>(R.id.reminder_priority)
    }

    val reminders = mutableListOf<Reminder>()

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
            deadline.text = reminder.deadline.toString()
            priority.text = reminder.priority.toString()
        }
    }

    override fun getItemCount(): Int {
        return reminders.size
    }


}