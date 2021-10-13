package com.reminders.topics

import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reminders.R

class TopicAdapter : RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {
    class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val inflatedLayout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.topic_view_holder, parent, false)

        return TopicViewHolder(inflatedLayout)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val topic = holder.apply {

        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}