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
    private lateinit var editTexts: List<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initEditTexts()
        initTextWatcher()
        initClickListeners()
    }

    private fun clearEditTexts() {
        editTexts.forEach {
            it.text.clear()
        }
    }

    private fun initEditTexts() {
        editTexts = listOf(
            binding.etBinary,
            binding.etOctal,
            binding.etDecimal,
            binding.etHexadecimal
        )
        for (et in editTexts) {
            et.onFocusChangeListener = CustomFocusListener()
        }
    }

    private fun initTextWatcher() {
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = focusedEditText?.text.toString()
                if (input.isEmpty()) return
                binding.apply {
                    when (focusedEditText?.id) {
                        etBinary.id -> setOutputForEditTexts(input, etBinary.id, BINARY_RADIX)
                        etOctal.id -> setOutputForEditTexts(input, etOctal.id, OCTAL_RADIX)
                        etDecimal.id -> setOutputForEditTexts(input, etDecimal.id, DECIMAL_RADIX)
                        etHexadecimal.id -> setOutputForEditTexts(input, etHexadecimal.id, HEXADECIMAL_RADIX)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        }
    }

    private fun initClickListeners() {
        binding.btnClear.setOnClickListener {
            clearEditTexts()
        }
    }

    private fun ActivityMainBinding.setOutputForEditTexts(
        input: String,
        exceptId: Int,
        radix: Int
    ) {
        etBinary.apply {
            if (id != exceptId)
                setText(toBinary(input, radix))
        }
        etOctal.apply {
            if (id != exceptId)
                setText(toOctal(input, radix))
        }
        etDecimal.apply {
            if (id != exceptId)
                setText(toDecimal(input, radix).toString())
        }

        etHexadecimal.apply {
            if (id != exceptId)
                setText(toHexadecimal(input, radix))
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