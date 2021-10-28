package com.reminders.topics

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.reminders.AppViewModel
import com.reminders.R
import com.reminders.data.enum.ColorSet
import com.reminders.data.model.Topic
import java.time.format.DateTimeFormatter


class TopicAdapter(
    private val dateFormatter: DateTimeFormatter,
    private val viewModel: AppViewModel,
    private val viewLifecycleOwner: LifecycleOwner
    ) : RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {

    inner class TopicViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.topic_name)
        var creationDate: TextView = view.findViewById(R.id.topic_creation_date)
        val reminderCount: TextView = view.findViewById(R.id.topic_reminder_count)
        var card: MaterialCardView = view.findViewById(R.id.topic_card)
    }

    val topics = mutableListOf<Topic>()

    fun updateData(newData: List<Topic>) {
        topics.clear()
        topics.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val inflatedLayout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.topic_view_holder, parent, false)

        return TopicViewHolder(inflatedLayout)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val topic = topics[position]
        holder.apply {
            name.text = topic.name
            name.setTextColor(ContextCompat.getColor(view.context, ColorSet.data[topic.color].textColorId))
            creationDate.text = view.resources.getString(
                    R.string.topic_creation_date,
                    topic.creationDate.format(dateFormatter)
                )
            creationDate.setTextColor(ContextCompat.getColor(view.context, ColorSet.data[topic.color].textColorId))
            reminderCount.setTextColor(ContextCompat.getColor(view.context, ColorSet.data[topic.color].textColorId))
            viewModel.getReminderCount(topic.id).observe(viewLifecycleOwner) {
                reminderCount.text = view.resources.getString(
                    R.string.reminder_count,
                    it.toString()
                )
            }
            card.setCardBackgroundColor(ContextCompat.getColor(view.context, ColorSet.data[topic.color].colorId))
            card.setOnClickListener {
                viewModel.updateTopicColor(topic.color)
                val action = ReadTopicFragmentDirections
                    .actionReadTopicFragmentToReadReminderFragment(topic.id, topic.name)
                view.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return topics.size
    }
}