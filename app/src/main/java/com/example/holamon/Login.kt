package com.example.holamon

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.holamon.stats.Friend
import com.example.holamon.stats.User

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        var editorName: EditText = findViewById(R.id.editTextPersonName)

        buttonLogin.setOnClickListener{
            if (!editorName.text.toString().isNullOrBlank()){
                val usr: User =
                    User(
                        editorName.text.toString()
                    )
                EstadisticasClass.addUser(usr)
                finish()
            } else {
                Toast.makeText(this,"Name empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
    }
}