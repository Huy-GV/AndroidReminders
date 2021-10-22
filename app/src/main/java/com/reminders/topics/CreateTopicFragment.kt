package com.reminders.topics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.reminders.AppViewModel
import com.reminders.R
import com.reminders.application.MyApplication
import com.reminders.data.model.Topic
import java.util.*


class CreateTopicFragment : Fragment() {
    //TODO: create a superclass fragment?
    private val appViewModel: AppViewModel by activityViewModels {
        val database = (activity?.application as MyApplication).database
        AppViewModel.Factory(
            database.reminderDao(),
            database.topicDao()
        )
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_create_topic, container, false)
        view.findViewById<Button>(R.id.create_topic_button).setOnClickListener {
            val editText = view.findViewById<EditText>(R.id.topic_input_field)
            if (editText.text.isBlank()) {
                editText.error = "Topic name must not be empty"
            } else {
                appViewModel.createTopic(editText.text.toString(), Calendar.getInstance().getTime())
                findNavController().navigateUp()
            }
        }

        return view
    }

}