package com.example.myworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    lateinit var cardTraining: CardView
    lateinit var cardReport: CardView
    lateinit var cardDiscover: CardView
    lateinit var cardSettings: CardView
    lateinit var cardExit: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardTraining = findViewById(R.id.cardTraining)
        cardTraining.setOnClickListener {
            val exercise = Intent(this, TrainingActivity::class.java)
            startActivity(exercise)
        }

        cardReport = findViewById(R.id.cardReport)
        cardReport.setOnClickListener {
            val report = Intent(this, ReportActivity::class.java)
            startActivity(report)
        }

        cardDiscover = findViewById(R.id.cardDiscover)
        cardDiscover.setOnClickListener {
            val discover = Intent(this, DiscoverActivity::class.java)
            startActivity(discover)
        }

        cardSettings = findViewById(R.id.cardSettings)
        cardSettings.setOnClickListener {
            val settings = Intent(this, SettingsActivity::class.java)
            startActivity(settings)
        }

        cardExit = findViewById(R.id.cardExit)
        cardExit.setOnClickListener {
            moveTaskToBack(true)
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
        }
    }
}