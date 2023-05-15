package com.example.myworkoutapp

import android.content.Intent
import android.icu.text.CaseMap.Lower
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class TrainingActivity : AppCompatActivity() {
    lateinit var cardFullBody: CardView
    lateinit var cardLowerBody: CardView
    lateinit var cardDiscover: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        cardFullBody = findViewById(R.id.cardFullBody)
        cardFullBody.setOnClickListener {
            val fullBody = Intent(this, FullBodyActivity::class.java)
            startActivity(fullBody)
        }

        cardLowerBody = findViewById(R.id.cardLowerBody)
        cardLowerBody.setOnClickListener {
            val lowerBody = Intent(this, LowerBodyActivity::class.java)
            startActivity(lowerBody)
        }

        cardDiscover = findViewById(R.id.cardDiscoverMore)
        cardDiscover.setOnClickListener {
            val discover = Intent(this, DiscoverActivity::class.java)
            startActivity(discover)
        }
    }
}