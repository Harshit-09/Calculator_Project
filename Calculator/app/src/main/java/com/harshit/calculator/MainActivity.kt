package com.harshit.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    // TextView used to display the input and output
    lateinit var mTextInputOutput: TextView

    // Represents whether the lastly pressed key is numeric or not
    var mLastNumeric: Boolean = false

    // Represent that current state is in error or not
    var mStateError: Boolean = false

    // If true, obstructs to add another dot
    var mLastDot: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTextInputOutput = findViewById(R.id.txtInput)
    }

    fun onDigit(view: View) {
        if (mStateError) {
            // If current state is Error, replace the error message
            mTextInputOutput.text = (view as Button).text
            mStateError = false
        } else {
            // If not, already there is a valid expression so append to it
            mTextInputOutput.append((view as Button).text)
        }
        // Set the flag
        mLastNumeric = true
    }

    fun onDecimalPoint(view: View) {
        if (mLastNumeric && !mStateError && !mLastDot) {
            mTextInputOutput.append(".")
            mLastNumeric = false
            mLastDot = true
        }
    }

    fun onOperator(view: View) {
        if (mLastNumeric && !mStateError) {
            mTextInputOutput.append((view as Button).text)
            mLastNumeric = false
            mLastDot = false    // Reset the dot flag
        }
    }

    fun onClear(view: View) {
        this.mTextInputOutput.text = ""
        mLastNumeric = false
        mStateError = false
        mLastDot = false
    }

    fun onEqual(view: View) {
        // If the current state is error, nothing to do.
        // If the last input is a number only, solution can be found.
        if (mLastNumeric && !mStateError) {
            // Read the expression
            val txt = mTextInputOutput.text.toString()
            // Create an Expression (A class from exp4j library)
            val expression = ExpressionBuilder(txt).build()
            try {
                // Calculate the result and display
                val result = expression.evaluate()
                mTextInputOutput.text = result.toString()
                mLastDot = true // Result contains a dot
            } catch (ex: ArithmeticException) {
                // Display an error message
                mTextInputOutput.text = "Error"
                mStateError = true
                mLastNumeric = false
            }
        }
    }

}