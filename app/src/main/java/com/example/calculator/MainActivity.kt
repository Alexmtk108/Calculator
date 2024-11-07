package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var resultDisplay: TextView
    private lateinit var inputDisplay: TextView

    private var currentInput: String = ""
    private var operator: String? = null
    private var firstOperand: Double? = null
    private var secondOperand: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация дисплеев
        resultDisplay = findViewById(R.id.resultDisplay)
        inputDisplay = findViewById(R.id.inputDisplay)

        // Инициализация кнопок
        val buttons = listOf(
            R.id.button_0 to "0",
            R.id.button_1 to "1",
            R.id.button_2 to "2",
            R.id.button_3 to "3",
            R.id.button_4 to "4",
            R.id.button_5 to "5",
            R.id.button_6 to "6",
            R.id.button_7 to "7",
            R.id.button_8 to "8",
            R.id.button_9 to "9",
            R.id.button_dot to ".",
            R.id.button_add to "+",
            R.id.button_subtract to "-",
            R.id.button_multiply to "*",
            R.id.button_divide to "/",
            R.id.button_percent to "%",
            R.id.button_equals to "="
        )

        buttons.forEach { (buttonId, value) ->
            findViewById<Button>(buttonId).setOnClickListener {
                handleInput(value)
            }
        }

        // Обработка очистки
        findViewById<Button>(R.id.button_clear).setOnClickListener {
            clearCalculator()
        }

        // Обработка переключателя темной темы (только для примера)
        /*val themeSwitch = findViewById<Switch>(R.id.themeSwitch)
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            toggleTheme(isChecked)
        }*/
    }
    private fun clearCalculator() {
        currentInput = ""
        operator = null
        firstOperand = null
        secondOperand = null
        resultDisplay.text = "0"
        inputDisplay.text = ""
    }

    private fun handleInput(value: String) {
        when (value) {
            "+", "-", "*", "/" -> handleOperator(value)
            "=" -> handleEquals()
            "%" -> handlePercent()
            else -> handleNumber(value)
        }
    }

    private fun handleOperator(operatorInput: String) {
        if (currentInput.isNotEmpty()) {
            firstOperand = currentInput.toDoubleOrNull()
            operator = operatorInput
            currentInput = ""
            updateDisplay()
        }
    }

    private fun handleEquals() {
        if (firstOperand != null && currentInput.isNotEmpty()) {
            secondOperand = currentInput.toDoubleOrNull()
            val result = when (operator) {
                "+" -> firstOperand!! + secondOperand!!
                "-" -> firstOperand!! - secondOperand!!
                "*" -> firstOperand!! * secondOperand!!
                "/" -> firstOperand!! / secondOperand!!
                else -> null
            }
            result?.let {
                resultDisplay.text = it.toString()
                firstOperand = null
                operator = null
                currentInput = ""
            }
        }
    }

    private fun handleNumber(number: String) {
        currentInput += number
        updateDisplay()
    }

    private fun handlePercent() {
        if (currentInput.isNotEmpty()) {
            val value = currentInput.toDoubleOrNull()
            value?.let {
                currentInput = (it / 100).toString()
                updateDisplay()
            }
        }
    }

    private fun updateDisplay() {
        inputDisplay.text = if (operator != null) {
            "${firstOperand?.toString()} $operator $currentInput"
        } else {
            currentInput
        }
    }



    /*private fun toggleTheme(isChecked: Boolean) {
        // Здесь может быть логика для смены темы (например, светлая или темная тема)
        // Для примера просто выводим текст.
        if (isChecked) {
            resultDisplay.text = "Светлая тема"
        } else {
            resultDisplay.text = "Темная тема"
        }
    }*/
}
