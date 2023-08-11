package com.example.die_app

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    // Shared preferences for storing data
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var resultTextView: TextView
    private lateinit var secondResultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize shared preferences
        sharedPreferences = getPreferences(MODE_PRIVATE)

        // Initialize UI components
        val diceOptions = arrayOf(4, 6, 8, 10, 12, 20)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, diceOptions)
        val customSidesSpinner = findViewById<Spinner>(R.id.customSidesSpinner)
        customSidesSpinner.adapter = adapter

        val rollButton = findViewById<Button>(R.id.rollButton)
        val rollTwiceButton = findViewById<Button>(R.id.rollTwiceButton)
        val diceRadioGroup = findViewById<RadioGroup>(R.id.diceRadioGroup)
        resultTextView = findViewById(R.id.resultTextView)
        secondResultTextView = findViewById(R.id.secondResultTextView)

        // Set click listeners for buttons
        rollButton.setOnClickListener { rollDice() }
        rollTwiceButton.setOnClickListener { rollDiceTwice() }

        // Set a listener for radio group changes
        diceRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            // Show the custom spinner when the 10-sided die is selected
            customSidesSpinner.visibility = if (checkedId == R.id.radio10) View.VISIBLE else View.GONE
        }
    }

    // Function to roll the dice and display result
    private fun rollDice() {
        val selectedDice = when (findViewById<RadioGroup>(R.id.diceRadioGroup).checkedRadioButtonId) {
            R.id.radio4 -> 4
            R.id.radio6 -> 6
            R.id.radio8 -> 8
            R.id.radio10 -> findViewById<Spinner>(R.id.customSidesSpinner).selectedItem as Int
            R.id.radio12 -> 12
            R.id.radio20 -> 20
            else -> 6
        }

        // Roll the dice and display the result
        val result = roll(selectedDice)
        val resultText = getString(R.string.result_text, result)
        resultTextView.text = resultText
    }

    // Function to roll the dice twice and display results
    private fun rollDiceTwice() {
        val selectedDice = when (findViewById<RadioGroup>(R.id.diceRadioGroup).checkedRadioButtonId) {
            R.id.radio4 -> 4
            R.id.radio6 -> 6
            R.id.radio8 -> 8
            R.id.radio10 -> findViewById<Spinner>(R.id.customSidesSpinner).selectedItem as Int
            R.id.radio12 -> 12
            R.id.radio20 -> 20
            else -> 6
        }

        val result1 = roll(selectedDice)
        val result2 = roll(selectedDice)

        val resultText1 = getString(R.string.result_text, result1)
        val resultText2 = getString(R.string.second_result_text, result2)

        resultTextView.text = resultText1
        secondResultTextView.visibility = View.VISIBLE
        secondResultTextView.text = resultText2
    }

    // Function to simulate rolling the dice and generate a random number
    private fun roll(maxValue: Int): Int {
        return (Math.random() * maxValue + 1).toInt()
    }
}
