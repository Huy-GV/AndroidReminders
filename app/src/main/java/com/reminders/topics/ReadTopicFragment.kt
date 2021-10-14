package com.reminders.topics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reminders.AppViewModel
import com.reminders.R
import com.reminders.application.MyApplication

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReadTopicFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReadTopicFragment : Fragment() {
    private val appViewModel: AppViewModel by activityViewModels {
        val database = (activity?.application as MyApplication).database
        AppViewModel.Factory(
            database.reminderDao(),
            database.topicDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_read_topic, container, false)

        val recycler = view.findViewById<RecyclerView>(R.id.topic_recycler)
        val topicAdapter = TopicAdapter()

        appViewModel
            .getTopics()
            .observe(this.viewLifecycleOwner) {
                topics -> topicAdapter.updateData(topics)
            }

        recycler.apply {
            adapter = topicAdapter
            layoutManager = LinearLayoutManager(this@ReadTopicFragment.context)
        }

        view.findViewById<Button>(R.id.create_topic_button).setOnClickListener {
            val action = ReadTopicFragmentDirections.actionReadTopicFragmentToCreateTopicFragment()
            this.findNavController().navigate(action)
        }

        return view
    }

}