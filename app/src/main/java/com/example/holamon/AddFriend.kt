package com.example.holamon

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.holamon.stats.Friend

class AddFriend : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)

        val buttonCreate: Button = findViewById(R.id.buttonAddFriendCreate)
        val buttonCancel: Button = findViewById(R.id.buttonAddFriendCancel)
        val editorFriendName: EditText = findViewById(R.id.editAddFriendName)
        val editorFriendSurname: EditText = findViewById(R.id.editAddFriendSurname)
        val editorFriendAge: EditText = findViewById(R.id.editAddFriendAge)
        val editorFriendGame: EditText = findViewById(R.id.editAddFriendGame)

        buttonCreate.setOnClickListener{
            if (!editorFriendName.text.toString().isNullOrBlank()
                && !editorFriendSurname.text.toString().isNullOrBlank()
                && !editorFriendAge.text.toString().isNullOrBlank()
                && !editorFriendGame.text.toString().isNullOrBlank()){
                val fri: Friend =
                    Friend(
                        editorFriendSurname.text.toString(),
                        editorFriendAge.text.toString().toInt(),
                        editorFriendGame.text.toString(),
                        editorFriendName.text.toString()
                    )
                EstadisticasClass.addFriend(fri)
                val intent = Intent()
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this,"name or Description empty", Toast.LENGTH_SHORT).show()
            }
        }

        buttonCancel.setOnClickListener{
            finish()
        }
    }
}