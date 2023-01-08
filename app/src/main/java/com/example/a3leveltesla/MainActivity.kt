package com.example.a3leveltesla

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Firebase.messaging.subscribeToTopic("notification")
            .addOnCompleteListener { task ->

            }
        val start = findViewById<Button>(R.id.start)
        start.setOnClickListener {
            startActivity(Intent(this, level1::class.java))
        }
    }
}