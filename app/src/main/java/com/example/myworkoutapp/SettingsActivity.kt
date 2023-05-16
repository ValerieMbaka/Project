package com.example.myworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myworkoutapp.databinding.ActivitySettingsBinding
import com.google.firebase.auth.FirebaseAuth


class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var user:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance()

        binding.cardLogout.setOnClickListener {
            user.signOut()
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            finish()
        }
    }
}