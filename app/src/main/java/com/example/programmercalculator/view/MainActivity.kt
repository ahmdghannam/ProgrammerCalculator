package com.example.programmercalculator.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.programmercalculator.databinding.ActivityMainBinding
import com.example.programmercalculator.toBinary
import com.example.programmercalculator.toDecimal
import com.example.programmercalculator.toHexadecimal
import com.example.programmercalculator.toOctal
import com.example.programmercalculator.utils.Constants.BINARY_CHARSET
import com.example.programmercalculator.utils.Constants.BINARY_RADIX
import com.example.programmercalculator.utils.Constants.DECIMAL_CHARSET
import com.example.programmercalculator.utils.Constants.DECIMAL_RADIX
import com.example.programmercalculator.utils.Constants.HEXADECIMAL_CHARSET
import com.example.programmercalculator.utils.Constants.HEXADECIMAL_RADIX
import com.example.programmercalculator.utils.Constants.OCTAL_CHARSET
import com.example.programmercalculator.utils.Constants.OCTAL_RADIX


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var textWatcher: TextWatcher? = null
    private var focusedEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initCallBacks()
    }

    private fun initCallBacks() {
        initEditTextsFocusListeners()
        initTextWatcher()
        initClickListeners()
    }

    private fun initEditTextsFocusListeners() {
        binding.apply {
            binaryEditText.onFocusChangeListener = CustomFocusListener()
            octalEditText.onFocusChangeListener = CustomFocusListener()
            decimalEditText.onFocusChangeListener = CustomFocusListener()
            hexadecimalEditText.onFocusChangeListener = CustomFocusListener()
        }
    }

    private fun initTextWatcher() {
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = focusedEditText?.text.toString().trim().uppercase()
                if (isValidInput(input)) setOutputForEditTexts(input)
                else showErrorMessageAtTheInputEditText()
            }

            override fun afterTextChanged(s: Editable?) {}

        }
    }

    private fun initClickListeners() {
        binding.btnClear.setOnClickListener {
            clearEditTexts()
        }
    }

    private fun clearEditTexts() {
        binding.apply {
            binaryEditText.text.clear()
            octalEditText.text.clear()
            decimalEditText.text.clear()
            hexadecimalEditText.text.clear()
        }
    }

    private fun showErrorMessageAtTheInputEditText() {
        focusedEditText?.error = "the input is invalid"
    }

    private fun removeEditTextsErrorMessagesIfExists() {
        binding.apply {
            binaryEditText.error = null
            octalEditText.error = null
            decimalEditText.error = null
            hexadecimalEditText.error = null
        }
    }

    private fun isValidInput(input: String): Boolean {
        if (input.isEmpty()) return false
        binding.apply {
            return when (focusedEditText?.id) {
                binaryEditText.id -> input.all { it in BINARY_CHARSET }
                octalEditText.id -> input.all { it in OCTAL_CHARSET }
                decimalEditText.id -> input.all { it in DECIMAL_CHARSET }
                hexadecimalEditText.id -> input.all { it in HEXADECIMAL_CHARSET }
                else -> false
            }
        }
    }

    private fun setOutputForEditTexts(input: String) {
        removeEditTextsErrorMessagesIfExists()
        binding.apply {
            when (focusedEditText?.id) {
                binaryEditText.id -> setOutputForEditTextsExceptBinary(input)
                octalEditText.id -> setOutputForEditTextsExceptOctal(input)
                decimalEditText.id -> setOutputForEditTextsExceptDecimal(input)
                hexadecimalEditText.id -> setOutputForEditTextsExceptHexadecimal(input)
            }
        }
    }


    private fun setOutputForEditTextsExceptHexadecimal(input: String) {
        binding.apply {
            octalEditText.setText(toOctal(input, HEXADECIMAL_RADIX))
            decimalEditText.setText(toDecimal(input, HEXADECIMAL_RADIX).toString())
            binaryEditText.setText(toBinary(input, HEXADECIMAL_RADIX))
        }
    }

    private fun setOutputForEditTextsExceptDecimal(input: String) {
        binding.apply {
            octalEditText.setText(toOctal(input, DECIMAL_RADIX))
            binaryEditText.setText(toBinary(input, DECIMAL_RADIX))
            hexadecimalEditText.setText(toHexadecimal(input, DECIMAL_RADIX))
        }
    }

    private fun setOutputForEditTextsExceptOctal(input: String) {
        binding.apply {
            binaryEditText.setText(toBinary(input, OCTAL_RADIX))
            decimalEditText.setText(toDecimal(input, OCTAL_RADIX).toString())
            hexadecimalEditText.setText(toHexadecimal(input, OCTAL_RADIX))
        }
    }

    private fun setOutputForEditTextsExceptBinary(input: String) {
        binding.apply {
            octalEditText.setText(toOctal(input, BINARY_RADIX))
            decimalEditText.setText(toDecimal(input, BINARY_RADIX).toString())
            hexadecimalEditText.setText(toHexadecimal(input, BINARY_RADIX))
        }
    }

    inner class CustomFocusListener : View.OnFocusChangeListener {
        override fun onFocusChange(v: View?, hasFocus: Boolean) {

            focusedEditText?.removeTextChangedListener(textWatcher)

            focusedEditText = v as EditText

            focusedEditText?.addTextChangedListener(textWatcher)

        }
    }


}