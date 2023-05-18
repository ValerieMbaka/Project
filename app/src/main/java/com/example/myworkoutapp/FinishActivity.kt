package com.example.myworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myworkoutapp.databinding.ActivityFinishBinding
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFinish.setOnClickListener{
            finish()
        }

        //on finishing the workout calling the addDateToDatabase() to add the date and time in database
        addDateToDatabase()

    }

    //method for adding date to database and formatting the date format
    private fun addDateToDatabase(){
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        Log.i("DATE:",""+dateTime)//making a log entry for testing

//        val sdf = SimpleDateFormat("dd-MMM-yyyy          HH:mm:ss", Locale.getDefault())//formatting date and time
        val sdf = SimpleDateFormat("dd-MMM-yyyy         HH:mm:ss aaa", Locale.getDefault())//formatting date and time
        val date = sdf.format(dateTime)

        val dbHandler = SqliteOpenHelper(this,null)
        dbHandler.addDate(date)
        Log.i("DATE: ", "Added")
    }



}
