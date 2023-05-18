package com.example.myworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.myworkoutapp.databinding.ActivityBmiactivityBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private val metricUnitsVIEW = "METRIC_UNIT_VIEW"
    private val usUnitsView = "US_UNIT_VIEW"
    private var currentVisibleView: String = metricUnitsVIEW

    private lateinit var binding: ActivityBmiactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculateUnits.setOnClickListener{

            //checking current unit system
            if(currentVisibleView == (metricUnitsVIEW)){
                //for metric Units
                if(validateMetricUnits()){
                    //converting height from centimeters to meters
                    val heightValue : Float = binding.etMetricUnitHeight.text.toString().toFloat() / 100
                    //weight is in kg
                    val weightValue : Float = binding.etMetricUnitWeight.text.toString().toFloat()

                    val bmiValue = weightValue / (heightValue * heightValue)
                    //calling the fun and passing the bmiValue as a parameter
                    displayBMIResult(bmiValue)

                }else{
                    Toast.makeText(this@BMIActivity, "Please enter valid values." , Toast.LENGTH_SHORT).show()
                }
            }else{
                //for US units
                if(validateUsUnits()){
                    val usUnitHeightValueFeet: String = binding.etUsUnitHeightFeet.text.toString()//Height in Feet
                    val usUnitHeightValueInch: String= binding.etUsUnitHeightInch.text.toString()//Height in inch
                    val usUnitWeightValue: Float = binding.etUsUnitWeight.text.toString().toFloat()//Weight in pound

                    //1 feet equals to 12 inch so converting feet height into inches to calculate the BMI
                    val usHeightValue = usUnitHeightValueInch.toFloat() + (usUnitHeightValueFeet.toFloat() * 12)

                    val bmiValue  = 703 * (usUnitWeightValue / (usHeightValue*usHeightValue))

                    //displaying the BMI value
                    displayBMIResult(bmiValue)
                }else{
                    Toast.makeText(this@BMIActivity,"Please enter valid values." , Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnExit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //making units system visible
        makeVisibleMetricUnitsView()
        binding.rgUnits.setOnCheckedChangeListener{ _, checkedId ->
            if(checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUSUnitsView()
            }
        }
    }
    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = metricUnitsVIEW
        binding.tilMetricUnitWeight.visibility = View.VISIBLE
        binding.tilMetricUnitHeight.visibility = View.VISIBLE

        //clearing the text and making the text empty
        binding.etMetricUnitHeight.text!!.clear()
        binding.etMetricUnitWeight.text!!.clear()


        binding.tilUSUnitWeight.visibility = View.GONE
        binding.llUsUnitsHeight.visibility = View.GONE

        binding.llDisplayBMIResult.visibility = View.INVISIBLE
    }
    //Method to make the US Units Visible
    private fun makeVisibleUSUnitsView(){
        currentVisibleView = usUnitsView
        binding.tilMetricUnitWeight.visibility = View.GONE
        binding.tilMetricUnitHeight.visibility = View.GONE

        //clearing the text and making the text empty
        binding.etUsUnitWeight.text!!.clear()
        binding.etUsUnitHeightFeet.text!!.clear()
        binding.etUsUnitHeightInch.text!!.clear()

        //US units views visible
        binding.tilUSUnitWeight.visibility = View.VISIBLE
        binding.llUsUnitsHeight.visibility = View.VISIBLE

        binding.llDisplayBMIResult.visibility = View.INVISIBLE
    }
    private fun displayBMIResult(bmi: Float){
        val bmiLabel : String
        val bmiDescription : String

        //Messages shown to user according to BMI values.
        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "Very severely Underweight"
            bmiDescription = "You need to take more care of your health! Eat more!"
        }else if(bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "Severely underweight"
            bmiDescription = "You need to take care of your health! Eat more!"
        }else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f)<= 0){
            bmiLabel = "Underweight"
            bmiDescription = "You need to take care of your health! Eat more!"
        }else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good fit shape!"
        }else if(bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0){
            bmiLabel = "Overweight"
            bmiDescription = "Oops! need to take care of yourself! Workout more"
        }else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0){
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your health! Workout daily"
        }else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <=0 ){
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "Oops! Your health is in a very dangerous condition! Act now!"
        }else{
            bmiLabel = "Obese Class ||| (Very Severely Overweight)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }


        //making text contents visible
        binding.llDisplayBMIResult.visibility = View.VISIBLE

        //Rounding off the bmi value and converting bmi value to string
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        //setting the text values
        binding.tvBMIValue.text = bmiValue
        binding.tvBMIType.text = bmiLabel
        binding.tvBMIDescription.text = bmiDescription
    }
    private fun validateMetricUnits(): Boolean{
        var isValid = true

        if(binding.etMetricUnitWeight.text.toString().isEmpty() || binding.etMetricUnitHeight.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }
    private fun validateUsUnits(): Boolean{
        var isValid = true
        if(binding.etUsUnitHeightFeet.text.toString().isEmpty() || binding.etUsUnitWeight.text.toString().isEmpty() || binding.etUsUnitHeightInch.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }
}