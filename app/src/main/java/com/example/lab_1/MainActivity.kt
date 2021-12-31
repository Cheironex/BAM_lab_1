package com.example.lab_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

const val USER_NAME = "com.example.lab_1.USER_NAME"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buttonClick(view: View) {
        val text = findViewById<EditText>(R.id.editTextTextPersonName)
        val name = text.text.toString()
        val intent = Intent(this, UserActivity::class.java).apply {
            putExtra(USER_NAME, name)
        }
        startActivity(intent)
    }
}