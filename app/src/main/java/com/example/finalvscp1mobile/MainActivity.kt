package com.example.finalvscp1mobile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.finalvscp1mobile.databinding.ActivityMainBinding

// Integrantes:
// rm99097- Bruno Ciccio (representante)
// rm97751 - Isa Corsi
// rm99488 - José Duarte
// rm99292 - Thalita Alencar
// rm551569 - Mari Cucco

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentInput: StringBuilder = StringBuilder()
    private var currentOperator: String = ""
    private var operand1: Double = 0.0
    private var operand2: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNumberButtons()
        setupOperationButtons()
        setupInvertButton()
    }

    private fun setupNumberButtons() {
        val numberButtonClickListener = View.OnClickListener { v ->
            val number = (v as Button).text.toString()
            currentInput.append(number)
            updateDisplay()
        }

        val numberButtonIds = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9
        )

        for (buttonId in numberButtonIds) {
            findViewById<Button>(buttonId).setOnClickListener(numberButtonClickListener)
        }
    }

    private fun setupOperationButtons() {
        val operationButtonClickListener = View.OnClickListener { v ->
            val operator = (v as Button).text.toString()

            if (currentInput.isNotEmpty()) {
                if (operand1 == 0.0) {
                    operand1 = currentInput.toString().toDouble()
                    currentInput.clear()
                } else {
                    operand2 = currentInput.toString().toDouble()
                    performOperation()
                }
                currentOperator = operator
            }
        }

        val operationButtonIds = listOf(
            R.id.buttonAdd, R.id.buttonSubtract,
            R.id.buttonMultiply, R.id.buttonDivide
        )

        for (buttonId in operationButtonIds) {
            findViewById<Button>(buttonId).setOnClickListener(operationButtonClickListener)
        }

        // Configurar botão de igual
        findViewById<Button>(R.id.buttonEquals).setOnClickListener {
            if (currentInput.isNotEmpty()) {
                operand2 = currentInput.toString().toDouble()
                performOperation()
                currentOperator = ""
            }
        }

        // Configurar botão de limpar
        findViewById<Button>(R.id.buttonClear).setOnClickListener {
            currentInput.clear()
            operand1 = 0.0
            operand2 = 0.0
            currentOperator = ""
            updateDisplay()
        }
    }

    private fun setupInvertButton() {
        findViewById<Button>(R.id.buttonInvert).setOnClickListener {
            invertSign()
        }
    }

    private fun invertSign() {
        if (currentInput.isNotEmpty()) {
            val inputNumber = currentInput.toString().toDouble()
            val invertedNumber = -inputNumber
            currentInput.clear()
            currentInput.append(invertedNumber)
            updateDisplay()
        } else if (operand2 != 0.0) {
            operand2 = -operand2
            updateDisplay()
        } else if (operand1 != 0.0) {
            operand1 = -operand1
            updateDisplay()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun performOperation() {
        when (currentOperator) {
            "+" -> {
                val result = operand1 + operand2
                displayResult(result)
            }
            "-" -> {
                val result = operand1 - operand2
                displayResult(result)
            }
            "*" -> {
                val result = operand1 * operand2
                displayResult(result)
            }
            "/" -> {
                if (operand2 != 0.0) {
                    val result = operand1 / operand2
                    displayResult(result)
                } else {
                    binding.textViewDisplay.text = "Error"
                }
            }
        }
    }

    private fun displayResult(result: Double) {
        binding.textViewDisplay.text = result.toString()
        currentInput.clear()
        operand1 = result
    }

    private fun updateDisplay() {
        binding.textViewDisplay.text = currentInput.toString()
    }
}

