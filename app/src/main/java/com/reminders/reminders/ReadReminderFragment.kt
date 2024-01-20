package com.reminders.reminders

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reminders.AppViewModel
import com.reminders.R
import com.reminders.application.MyApplication
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.reminders.data.enum.Action
import com.reminders.data.enum.ColorSet
import com.reminders.topics.CreateUpdateTopicDialogFragment
import com.reminders.topics.DeleteTopicDialogFragment

@RequiresApi(Build.VERSION_CODES.O)
class ReadReminderFragment : Fragment() {

    private val args: ReadReminderFragmentArgs by navArgs()

    private val viewModel: AppViewModel by activityViewModels {
        val database = (activity?.application as MyApplication).database
        AppViewModel.Factory(
            database.reminderDao(),
            database.topicDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.reminder_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_topic -> {
                DeleteTopicDialogFragment(
                    args.topicId,
                    viewModel,
                    resources.getString(R.string.delete_topic_warning, args.topicName)
                )
                    .show(
                        parentFragmentManager,
                        DeleteTopicDialogFragment.TAG,
                    )
            }
            R.id.edit_topic -> {
                CreateUpdateTopicDialogFragment(
                    Action.UPDATE,
                    viewModel,
                    args.topicId,
                    args.topicName,
                )
                    .show(
                        parentFragmentManager,
                        CreateUpdateTopicDialogFragment.TAG
                    )
            }
        }
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_read_reminder, container, false)

        view.setBackgroundResource(ColorSet.data[viewModel.topicColor.value!!].colorId)
        val recycler = view.findViewById<RecyclerView>(R.id.reminder_recycler)
        val reminderAdapter = ReminderAdapter(
            viewModel,
            resources.getString(R.string.update_reminder_label)
        )

        viewModel.apply {
            getReminders(args.topicId).observe(this@ReadReminderFragment.viewLifecycleOwner) {
                reminders -> reminderAdapter.updateData(reminders)
            }

            topicColor.observe(this@ReadReminderFragment.viewLifecycleOwner) {
                view.setBackgroundResource(ColorSet.data[viewModel.topicColor.value!!].colorId)
            }
        }

        recycler.apply {
            adapter = reminderAdapter
            layoutManager = LinearLayoutManager(this@ReadReminderFragment.context)
        }

        view.findViewById<FloatingActionButton>(R.id.create_reminder_button).setOnClickListener {
            val action = ReadReminderFragmentDirections
                .actionReadReminderFragmentToCreateUpdateReminderFragment(
                    args.topicId,
                    Action.CREATE,
                    null,
                    resources.getString(R.string.add_reminder_label) + args.topicName
                )
            findNavController().navigate(action)
        }

        return view
    }
}