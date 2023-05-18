package com.example.myworkoutapp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myworkoutapp.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

/**
 * There are two timers used
 * 1) RestTimer: This timer is for the user to get ready upcoming exercise
 * 2) ExerciseTimer: This timer is for the user while performing the exercise
 *
 * ExerciseActivity is extended with TextToSpeech OnInitListener
 */


class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    //variable for rest timer
    private var restTimer: CountDownTimer?=null
    private var restProgress = 0
    private var restTimerDuration: Long = 5

    private var exerciseTimer: CountDownTimer?=null
    private var exerciseProgress = 10

    private var exerciseTimerDuration : Long = 2
    private var exerciseList: ArrayList<ExerciseModel>?=null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null

    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    private lateinit var binding: ActivityExerciseBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tts = TextToSpeech(this,this)

        exerciseList = Constants.defaultExerciseList()
        setupRestView()

        setupExerciseStatusRecyclerView()
        onDestroy()
        setRestProgressBar()
        setExerciseProgressBar()
        setupRestView()
        setupExerciseView()

    }

    override fun onDestroy(){
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }
        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player != null){
            player!!.stop()
        }
        super.onDestroy()
    }

    private fun setRestProgressBar(){
        binding.progressBar.progress = restProgress
        restTimer = object: CountDownTimer(restTimerDuration*1000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.progressBar.progress = restTimerDuration.toInt() - restProgress
                binding.tvTimer.text = (restTimerDuration.toInt() - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                setupExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar(){
        binding.progressBarExercise.progress = exerciseProgress
        exerciseTimer = object: CountDownTimer(exerciseTimerDuration*1000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding.progressBar.progress = exerciseTimerDuration.toInt() - exerciseProgress
                binding.tvExerciseTimer.text = (exerciseTimerDuration.toInt() - exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList?.size!! - 1){
                    //if all exercise performance is not completed
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }else{
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    private fun setupRestView(){
        try{
            player = MediaPlayer.create(applicationContext,R.raw.background)
            player!!.isLooping = false
            player!!.start()

        }catch (e: Exception){
            e.printStackTrace()
        }

        binding.llRestView.visibility = View.VISIBLE
        binding.llExerciseView.visibility = View.GONE


        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        binding.tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()
        setRestProgressBar()
    }

    private fun setupExerciseView(){

        binding.llRestView.visibility = View.GONE
        binding.llExerciseView.visibility = View.VISIBLE


        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())


        setExerciseProgressBar()

        binding.ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding.tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
    }


    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","Language specified is not supported")
                Toast.makeText(this,"Language not Supported",Toast.LENGTH_SHORT).show()
            }else{
                Log.e("TTS","Initialization Failed!")
            }
        }
    }


    private fun speakOut(text: String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun setupExerciseStatusRecyclerView(){
        binding.rvExerciseStatus.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter = ExerciseStatusAdapter(this, exerciseList!!)
        binding.rvExerciseStatus.adapter = exerciseAdapter
    }

}
