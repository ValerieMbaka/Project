package com.example.myworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class ReportActivity : AppCompatActivity() {
    private lateinit var cardBMI:CardView
    private lateinit var cardHistory:CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        cardBMI = findViewById(R.id.cardBMI)
        cardBMI.setOnClickListener {
            val bmi = Intent(this, BMIActivity::class.java)
            startActivity(bmi)
        }

        cardHistory = findViewById(R.id.cardHistory)
        cardHistory.setOnClickListener {
            val history = Intent(this, HistoryActivity::class.java)
            startActivity(history)
        }

    }
}