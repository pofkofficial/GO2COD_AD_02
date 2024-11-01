package com.example.calculatorapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var input: String = ""
    private var operator: String? = null
    private var operand1: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        // Number buttons (0-9)
        val buttons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9
        )
        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                appendToInput((it as Button).text.toString())
            }
        }

        // Operator buttons
        findViewById<Button>(R.id.buttonAdd).setOnClickListener { setOperator("+") }
        findViewById<Button>(R.id.buttonSubtract).setOnClickListener { setOperator("-") }
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener { setOperator("×") }
        findViewById<Button>(R.id.buttonDivide).setOnClickListener { setOperator("÷") }

        // Other buttons
        findViewById<Button>(R.id.buttonDecimal).setOnClickListener { appendToInput(".") }
        findViewById<Button>(R.id.buttonClear).setOnClickListener { clear() }
        findViewById<Button>(R.id.buttonDelete).setOnClickListener { delete() }
        findViewById<Button>(R.id.buttonEquals).setOnClickListener { calculate() }
    }

    private fun appendToInput(value: String) {
        if (value == "." && input.contains(".")) return  // Prevent multiple decimals
        input += value
        display.text = input
    }

    private fun setOperator(op: String) {
        if (input.isNotEmpty()) {
            operand1 = input.toDoubleOrNull()
            operator = op
            input = ""
            display.text = op  // Optional: show operator on display
        }
    }

    private fun calculate() {
        val operand2 = input.toDoubleOrNull()
        if (operand1 == null || operator == null || operand2 == null) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
            return
        }

        val result = when (operator) {
            "+" -> operand1!! + operand2
            "-" -> operand1!! - operand2
            "×" -> operand1!! * operand2
            "÷" -> if (operand2 != 0.0) operand1!! / operand2 else "Cannot divide by zero".also { display.text = it }
            else -> null
        }

        if (result != null) {
            display.text = result.toString()
            operand1 = result as Double?
            input = ""
            operator = null
        }
    }

    private fun clear() {
        input = ""
        operator = null
        operand1 = null
        display.text = "0"
    }

    private fun delete() {
        if (input.isNotEmpty()) {
            input = input.dropLast(1)
            display.text = if (input.isEmpty()) "0" else input
        }
    }
}