package com.reminders.topics

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.reminders.AppViewModel
import com.reminders.R
import com.reminders.application.MyApplication
import com.reminders.reminders.ReadReminderFragment


class TopicUpdateFragment : Fragment() {

    private var topicId: Int? = null
    private var topicName: String? = null
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
            topicName = it.getString(TOPIC_NAME)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_topic_update, container, false)
        val editText = view.findViewById<EditText>(R.id.topic_name_field)
        editText.setText(topicName)
        view.findViewById<Button>(R.id.update_button).setOnClickListener {
                val newName = editText.text.toString()
                if (newName.isBlank()) {
                    editText.error = "Topic name cant be blank"
                } else {
                    appViewModel.updateTopic(topicId!!, newName)
                    Log.d("topic", topicId.toString())
                    findNavController().navigateUp()
                }
            }

        return view
    }

    companion object {
        val TOPIC_ID = "update_topic_id"
        val TOPIC_NAME = "update_topic_name"
    }


}