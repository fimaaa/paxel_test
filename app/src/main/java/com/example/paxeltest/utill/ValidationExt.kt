package com.example.paxeltest.utill

import android.content.res.Resources
import android.util.Patterns
import java.text.DecimalFormat
import java.util.regex.Pattern
import kotlin.math.roundToInt

fun String.isFullNameValid(): Boolean {
    val p = Pattern.compile("^[a-z A-Z ' -]{0,30}\$")
    return p.matcher(this).matches()
}

fun String.isMobileNumberValid(): Boolean {
    val p = Pattern.compile("^((?:\\+62|62)|0)[8]{1}[0-9]{6,12}\$")
    return p.matcher(this).matches()
}

fun String.isEmailValid(): Boolean = this.isNotEmpty() &&
        Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isPasswordValid(): Boolean = this.isNotEmpty() && this.length >= 8 &&
        (Pattern.compile("^(?=.*[a-z A-Z])(?=.*[A-Z])(?=.*[0-9])[a-z A-Z0-9\$&+,:;=\\\\?@#|/'<>.^*()%!-]+$")
            .matcher(this).matches())

fun String.isPasswordTmoneyValid(): Boolean = this.isNotEmpty() && this.length >= 8 &&
        (Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}\$")
            .matcher(this).matches())

fun String.maskPhoneNumber(): String {
    return "••••••••" + this.takeLast(4)
}

fun Int.pxToDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density).toInt()
}

fun Int.dpToPx(): Int {
    return (this.toFloat() * Resources.getSystem().displayMetrics.density).roundToInt()
}

fun String.convertPrice(): String {
    val formatter = DecimalFormat("#,###")
    val m = this
    val formattedNumber = "Rp${formatter.format(m.toDouble())}"

    return formattedNumber.replace(",", ".")
}

fun Double.convertPrice(): String {
    val formatter = DecimalFormat("#,###")
    val m = Math.abs(this)
    val formattedNumber = "Rp${formatter.format(m)}"

    return formattedNumber.replace(",", ".")
}

fun String.isPassportValid(): Boolean {
    val p = Pattern.compile("^(?!^0+\$)[A-Z]{1}[0-9]{7}\$")
    return p.matcher(this).matches()
}

fun String.isKtpValid(): Boolean {
    val p = Pattern.compile("^[0-9]{16}\$")
    return p.matcher(this).matches()
}

fun String.isSimValid(): Boolean {
    val p = Pattern.compile("^[0-9]{12}\$")
    return p.matcher(this).matches()
}