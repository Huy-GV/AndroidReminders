package com.reminders.topics

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.reminders.AppViewModel
import com.reminders.R
import com.reminders.data.enum.Action
import com.reminders.data.enum.ColorSet
import java.time.LocalDate

class CreateUpdateTopicDialogFragment(
    private val action: Action,
    private val viewModel: AppViewModel,
    private val topicId: Int = 0,
    private val topicName: String = "",
) : DialogFragment() {

    private lateinit var nameField: TextInputEditText
    private lateinit var addOrUpdateButton: Button
    private lateinit var colorBlock: View
    private lateinit var seekBar: SeekBar
    private var maxTopicLength: Int = 0
    private var topicColor: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_update_topic, container, false)

        colorBlock = view.findViewById(R.id.color_block)
        seekBar = view.findViewById(R.id.color_seek_bar)
        addOrUpdateButton = view.findViewById(R.id.positive_topic_button)
        nameField = view.findViewById(R.id.topic_name_field)
        nameField.setText(topicName)

        viewModel.topicColor.value?.let { topicColor = it }
        maxTopicLength = view.resources.getInteger(R.integer.max_topic_name)

        view.findViewById<Button>(R.id.cancel_topic_button).setOnClickListener {
            dismiss()
        }

        when (action) {
            Action.CREATE -> setCreateTopic()
            Action.UPDATE -> setUpdateTopic()
        }

        colorBlock.setBackgroundResource(ColorSet.data[topicColor].colorId)
        seekBar.apply {
            progress = topicColor
            max = ColorSet.data.size - 1
            setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    colorBlock.setBackgroundResource(ColorSet.data[progress].colorId)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) { }
                override fun onStopTrackingTouch(seekBar: SeekBar?) { }
            })
        }

        return view
    }

    private fun setUpdateTopic() {
        addOrUpdateButton.text = resources.getString(R.string.save_changes)
        addOrUpdateButton.setOnClickListener {
            if (validTopicName()) {
                viewModel.updateTopic(topicId, nameField.text.toString(), seekBar.progress)
                viewModel.updateTopicColor(seekBar.progress)
                dismiss()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setCreateTopic() {
        addOrUpdateButton.text = resources.getString(R.string.add_new)
        addOrUpdateButton.setOnClickListener {
            if (validTopicName()) {
                viewModel.createTopic(
                    nameField.text.toString(),
                    LocalDate.now(),
                    seekBar.progress
                )

                dismiss()
            }
        }
    }

    private fun validTopicName() : Boolean {
        if (nameField.text.toString().isEmpty()) {
            nameField.error = resources.getString(R.string.empty_topic_name_error)
            return false
        } else if (nameField.text!!.length > maxTopicLength) {
            nameField.error = resources.getString(R.string.topic_name_max_error)
            return false
        }

        return true
    }

    companion object {
        const val TAG = "CreateUpdateTopicDialogFragment"
    }
}