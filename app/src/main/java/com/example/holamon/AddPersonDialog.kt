package com.example.holamon

import android.app.Dialog
import android.content.Context
import android.widget.Button

class AddPersonDialog {
    companion object {
        fun buildDialog(
            context: Context,
             closeActivity: () -> Unit
        ): Dialog {

            val dialog = Dialog(context)
            dialog.setContentView(R.layout.add_person_dialog)

            val acceptButton = dialog.findViewById<Button>(R.id.closeActivity)
            val closeButton = dialog.findViewById<Button>(R.id.dismissActivity)

            acceptButton.setOnClickListener {
                closeActivity()
            }
            
            closeButton.setOnClickListener {
                dialog.dismiss()
            }

            return dialog
        }
    }
}
