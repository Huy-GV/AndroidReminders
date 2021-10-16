package com.reminders.topics

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.reminders.AppViewModel
import com.reminders.R



class TopicDeletionDialogFragment(
    private val topicId: Int,
    private val appViewModel: AppViewModel
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic_deletion_dialog, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("Are you sure you want to delete this topic?")
            .setPositiveButton("DELETE") { _,_ ->
                appViewModel.deleteTopic(topicId)
                findNavController().navigateUp()
                dismiss()
            }
            .setNegativeButton("CANCEL") { _, _ -> dismiss() }
            .create()

    companion object {
        val TAG = "DeleteTopicDialog"
    }
}