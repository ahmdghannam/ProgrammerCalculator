package com.example.programmercalculator

import kotlin.math.pow

private fun binaryToDecimal(binary: String): Long {
    var decimal = 0L
    var power = binary.length - 1
    for (digit in binary) {
        decimal += digit.toString().toInt() * 2.0.pow(power).toInt()
        power--
    }
    return decimal
}

private fun binaryToOctal(binary: String): String {
    val decimal = binaryToDecimal(binary)
    var octal = ""
    var quotient = decimal
    while (quotient > 0) {
        octal = (quotient % 8).toString() + octal
        quotient /= 8
    }
    return octal
}

private fun binaryToHexadecimal(binary: String): String {
    val decimal = binaryToDecimal(binary)
    var hexadecimal = ""
    var quotient = decimal
    while (quotient > 0) {
        val remainder = quotient % 16
        if (remainder < 10) {
            hexadecimal = remainder.toString() + hexadecimal
        } else {
            hexadecimal = ('A'.code + remainder - 10).toInt().toChar() + hexadecimal
        }
        quotient /= 16
    }
    return hexadecimal
}

private fun decimalToBinary(decimal: String): String {
    val number = decimal.toLong()
    var binary = ""
    var quotient = number
    while (quotient > 0) {
        binary = (quotient % 2).toString() + binary
        quotient /= 2
    }
    return binary
}

private fun octalToBinary(octal: String): String {
    var decimal = 0
    for ((power, i) in (octal.length - 1 downTo 0).withIndex()) {
        val digit = octal[i].toString().toInt()
        decimal += digit * 8.0.pow(power).toInt()
    }
    return decimalToBinary(decimal.toString())
}

private fun hexadecimalToBinary(hexadecimal: String): String {
    var decimal = 0
    for ((power, i) in (hexadecimal.length - 1 downTo 0).withIndex()) {
        val value = when (val digit = hexadecimal[i]) {
            in '0'..'9' -> digit.toString().toInt()
            else -> (digit.uppercaseChar() - 'A' + 10)
        }
        decimal += value * 16.0.pow(power).toInt()
    }
    return decimalToBinary(decimal.toString())
}

fun toBinary(input: String, radix: Int): String {
    return when (radix) {
        2 -> input
        8 -> octalToBinary(input)
        10 -> decimalToBinary(input)
        16 -> hexadecimalToBinary(input)
        else -> "error"
    }

}

fun toOctal(input: String, radix: Int): String {
    val binaryValue = toBinary(input, radix)
    return binaryToOctal(binaryValue)
}

fun toDecimal(input: String, radix: Int): Long {
    val binaryValue = toBinary(input, radix)
    return binaryToDecimal(binaryValue)
}

fun toHexadecimal(input: String, radix: Int): String {
    val binaryValue = toBinary(input, radix)
    return binaryToHexadecimal(binaryValue)
}