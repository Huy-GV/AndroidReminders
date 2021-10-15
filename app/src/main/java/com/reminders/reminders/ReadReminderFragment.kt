package com.reminders.reminders

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reminders.AppViewModel
import com.reminders.R
import com.reminders.application.MyApplication
import android.view.Menu
import android.view.MenuItem
import com.reminders.topics.TopicDeletionDialogFragment



class ReadReminderFragment : Fragment() {
    private var topicId: Int? = null
    private val appViewModel: AppViewModel by activityViewModels {
        val database = (activity?.application as MyApplication).database
        AppViewModel.Factory(
            database.reminderDao(),
            database.topicDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            topicId = it.getInt(TOPIC_ID)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.reminder_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_topic -> {
                TopicDeletionDialogFragment(topicId!!, appViewModel, findNavController())
                    .show(requireFragmentManager(), TopicDeletionDialogFragment.TAG)
            }
            R.id.edit_topic -> {

            }
        }
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_read_reminder, container, false)

        val recycler = view.findViewById<RecyclerView>(R.id.reminder_recycler)
        val reminderAdapter = ReminderAdapter(appViewModel)

        appViewModel
            .getReminders(topicId!!)
            .observe(this.viewLifecycleOwner) {
                    reminders -> reminderAdapter.updateData(reminders)
            }

        recycler.apply {
            adapter = reminderAdapter
            layoutManager = LinearLayoutManager(this@ReadReminderFragment.context)
        }

        view.findViewById<Button>(R.id.create_reminder_button).setOnClickListener {
            val action =
                ReadReminderFragmentDirections
                    .actionReadReminderFragmentToCreateReminderFragment(topicId!!)
            findNavController().navigate(action)
        }

        return view
    }

    companion object {
        val TOPIC_ID = "topic_id"
    }
}