package com.example.programmercalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.programmercalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var textWatcher: TextWatcher? = null
    private var focusedEditText: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initEditTextsFocusListeners()
        initTextWatcher()
        initClickListeners()
    }

    private fun clearEditTexts() {
        binding.apply {
            binaryEditText.text.clear()
            octalEditText.text.clear()
            decimalEditText.text.clear()
            hexadecimalEditText.text.clear()
        }
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
                val input = focusedEditText?.text.toString().trim()
                setOutputForEditTexts(input)
            }

            override fun afterTextChanged(s: Editable?) {}

            private fun setOutputForEditTexts(input: String) {
                if (input.isEmpty()) return

                binding.apply {
                    when (focusedEditText?.id) {
                        binaryEditText.id -> setOutputForEditTextsExceptBinaryOne(input)
                        octalEditText.id -> setOutputForEditTextsExceptOctalOne(input)
                        decimalEditText.id -> setOutputForEditTextsExceptDecimalOne(input)
                        hexadecimalEditText.id -> setOutputForEditTextsExceptHexadecimalOne(input)
                    }
                }
            }

            private fun setOutputForEditTextsExceptHexadecimalOne(input: String) {
                binding.apply {
                    octalEditText.setText(toOctal(input, HEXADECIMAL_RADIX))
                    decimalEditText.setText(toDecimal(input, HEXADECIMAL_RADIX).toString())
                    binaryEditText.setText(toBinary(input, HEXADECIMAL_RADIX))
                }
            }

            private fun setOutputForEditTextsExceptDecimalOne(input: String) {
                binding.apply {
                    octalEditText.setText(toOctal(input, DECIMAL_RADIX))
                    binaryEditText.setText(toBinary(input, DECIMAL_RADIX))
                    hexadecimalEditText.setText(toHexadecimal(input, DECIMAL_RADIX))
                }
            }

            private fun setOutputForEditTextsExceptOctalOne(input: String) {
                binding.apply {
                    binaryEditText.setText(toBinary(input, OCTAL_RADIX))
                    decimalEditText.setText(toDecimal(input, OCTAL_RADIX).toString())
                    hexadecimalEditText.setText(toHexadecimal(input, OCTAL_RADIX))
                }
            }

            private fun setOutputForEditTextsExceptBinaryOne(input: String) {
                binding.apply {
                    octalEditText.setText(toOctal(input, BINARY_RADIX))
                    decimalEditText.setText(toDecimal(input, BINARY_RADIX).toString())
                    hexadecimalEditText.setText(toHexadecimal(input, BINARY_RADIX))
                }
            }
        }
    }

    private fun initClickListeners() {
        binding.btnClear.setOnClickListener {
            clearEditTexts()
        }
    }


    inner class CustomFocusListener : View.OnFocusChangeListener {
        override fun onFocusChange(v: View?, hasFocus: Boolean) {

            focusedEditText?.removeTextChangedListener(textWatcher)

            focusedEditText = v as EditText

            focusedEditText?.addTextChangedListener(textWatcher)

        }
    }

    companion object {
        const val BINARY_RADIX = 2
        const val OCTAL_RADIX = 8
        const val DECIMAL_RADIX = 10
        const val HEXADECIMAL_RADIX = 16
    }

}