package com.reminders.topics

import android.content.res.Resources
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.reminders.R
import com.reminders.data.model.Topic
import java.time.format.DateTimeFormatter

class TopicAdapter(
    private val navController: NavController,
    private val dateFormatter: DateTimeFormatter,
    private val resources: Resources
    ) : RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {

    inner class TopicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val topic = topics[position]
        holder.apply {
            name.text = topic.name
            creationDate.text = resources.getString(
                    R.string.topic_creation_date,
                    topic.creationDate.format(dateFormatter)
                )
            card.setOnClickListener {
                navController.navigate(
                    ReadTopicFragmentDirections
                        .actionReadTopicFragmentToReadReminderFragment(topic.id, topic.name))
            }
        }
    }

    override fun getItemCount(): Int {
        return topics.size
    }
}