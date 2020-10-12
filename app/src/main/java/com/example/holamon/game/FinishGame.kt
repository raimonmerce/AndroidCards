package com.example.holamon.game

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.TextView
import com.example.holamon.R

class FinishGame {
    companion object {
        fun buildDialog(
            context: Context,
            time: String,
            startGame: () -> Unit
        ): Dialog {

            val dialog = Dialog(context)
            dialog.setContentView(R.layout.finish_game)

            val acceptButton = dialog.findViewById<Button>(R.id.restartGame)
            val closeButton = dialog.findViewById<Button>(R.id.finishGame)

            val mainText: TextView = dialog.findViewById(R.id.mainText)
            val timeText: TextView = dialog.findViewById(R.id.timeResult)

            mainText.text = "Finished"
            timeText.text = time
            acceptButton.setOnClickListener {
                startGame()
                dialog.dismiss()
            }
            
            closeButton.setOnClickListener {
                dialog.dismiss()
            }

            return dialog
        }
    }
}
