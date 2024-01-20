package com.reminders.topics

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.reminders.AppViewModel
import com.reminders.R

class DeleteTopicDialogFragment(
    private val topicId: Int,
    private val viewModel: AppViewModel,
    private val deleteWarning: String
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(deleteWarning)
            .setPositiveButton(resources.getString(R.string.delete)) { _,_ ->
                viewModel.deleteTopic(topicId)
                findNavController().navigateUp()
                dismiss()
            }
            .setNegativeButton(resources.getString(R.string.cancel_button)) { _, _ -> dismiss() }
            .create()

    companion object {
        const val TAG = "DeleteTopicDialog"
    }
}