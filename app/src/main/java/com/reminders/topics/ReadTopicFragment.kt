package com.reminders.topics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.reminders.AppViewModel
import com.reminders.R

import com.reminders.application.MyApplication
import com.reminders.data.enum.Action

class ReadTopicFragment : Fragment() {

    private val appViewModel: AppViewModel by activityViewModels {
        val database = (activity?.application as MyApplication).database
        AppViewModel.Factory(
            database.reminderDao(),
            database.topicDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_read_topic, container, false)

        val recycler = view.findViewById<RecyclerView>(R.id.topic_recycler)
        val topicAdapter = TopicAdapter(appViewModel.dateFormatter, appViewModel, viewLifecycleOwner)
        appViewModel.clearTopicColor()
        appViewModel
            .getTopics()
            .observe(this.viewLifecycleOwner) {
                topics -> topicAdapter.updateData(topics)
            }

        recycler.apply {
            adapter = topicAdapter
            layoutManager = GridLayoutManager(this@ReadTopicFragment.context, 2)
        }

        view.findViewById<FloatingActionButton>(R.id.create_topic_button).setOnClickListener {
            CreateUpdateTopicDialogFragment(
                Action.CREATE,
                appViewModel,
            )
                .show(
                    parentFragmentManager,
                    CreateUpdateTopicDialogFragment.TAG
                )
        }

        return view
    }

}