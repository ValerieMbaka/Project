package com.example.myworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingsActivity : AppCompatActivity() {
    lateinit var cardProfile: CardView
    lateinit var cardLogout: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        cardProfile = findViewById(R.id.cardProfile)
        cardProfile.setOnClickListener {
            val profile = Intent(this, ProfileActivity::class.java)
            startActivity(profile)
        }

        cardLogout = findViewById(R.id.cardLogout)
        cardLogout.setOnClickListener {
            Firebase.auth.signOut()
        }
    }
}