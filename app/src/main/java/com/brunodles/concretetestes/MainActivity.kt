package com.brunodles.concretetestes

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val passwordValidator = PasswordValidator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login.setOnClickListener {
            val password = password.text
            if (username.text.isEmpty())
                message.text = "Empty Username"
            else if (password.isEmpty())
                message.text = "Empty Password"
            else if (passwordValidator.validate(password.toString()))
                startActivity(Intent(this, HomeActivity::class.java))
            else
                message.text = "Invalid Username or Password"
        }
    }
}
