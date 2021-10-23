package com.reminders.topics

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.reminders.AppViewModel
import com.reminders.R
import com.reminders.data.enum.Action
import java.time.LocalDate


class CreateUpdateTopicDialogFragment(
    private val action: Action,
    private val viewModel: AppViewModel,
    private val topicId: Int = 0,
    private val topicName: String = ""
) : DialogFragment() {

    private lateinit var nameField: TextInputEditText
    private lateinit var positiveButton: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_update_topic, container, false)

        positiveButton = view.findViewById(R.id.positive_topic_button)
        nameField = view.findViewById(R.id.topic_name_field)
        nameField.setText(topicName)

        view.findViewById<Button>(R.id.cancel_topic_button).setOnClickListener {
            dismiss()
        }

        when (action) {
            Action.CREATE -> setCreateTopic()
            Action.UPDATE -> setUpdateTopic()
        }

        return view
    }

    private fun setUpdateTopic() {
        positiveButton.text = resources.getString(R.string.save_changes)
        positiveButton.setOnClickListener {
            if (validTopicName()) {
                viewModel.updateTopic(topicId, nameField.text.toString())
                dismiss()
            }
        }
    }

    fun validTopicName() : Boolean {
        if (nameField.text.toString().isEmpty()) {
            nameField.error = resources.getString(R.string.empty_topic_name_error)
            return false
        } else if (nameField.text!!.length > 10) {
            nameField.error = resources.getString(R.string.topic_name_max_error)
            return false
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setCreateTopic() {
        positiveButton.text = resources.getString(R.string.add_new)
        positiveButton.setOnClickListener {

            if (validTopicName()) {
                viewModel.createTopic(nameField.text.toString(), LocalDate.now())
                dismiss()
            }
        }

    }

    companion object {
        const val TAG = "create_update_topic"
    }
}