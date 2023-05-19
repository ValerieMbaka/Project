package com.example.myworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class WorkoutActivity : AppCompatActivity() {
    private lateinit var myWebWorkout:WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)
        myWebWorkout = findViewById(R.id.mWebWorkout)
        val webSettings = myWebWorkout.settings
        webSettings.javaScriptEnabled = true
        myWebWorkout.loadUrl("https://www.fitnessblender.com/workouts")
    }
}