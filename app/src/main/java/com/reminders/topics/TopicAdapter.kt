package com.reminders.topics

import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.reminders.R
import com.reminders.data.model.Topic
import java.time.format.DateTimeFormatter

class TopicAdapter(
    private val navController: NavController,
    private val dateFormatter: DateTimeFormatter
    ) : RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {

    class TopicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.topic_name)
        var creationDate: TextView = view.findViewById(R.id.topic_creation_date)
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

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val topic = topics[position]
        holder.apply {
            name.text = topic.name
            creationDate.text = topic.creationDate.format(dateFormatter)
            card.setOnClickListener {
                val action = ReadTopicFragmentDirections
                    .actionReadTopicFragmentToReadReminderFragment(topic.id, topic.name)
                    navController.navigate(action)
            }
        }

    }

    override fun getItemCount(): Int {
        return topics.size
    }


}