package com.example.paxeltest.utill

import android.content.Context
import com.example.paxeltest.R
import java.text.NumberFormat
import java.util.*

object CurrencyUtils {
    fun convertDoubletoFormatNumber(number: String?): String {
        val format = NumberFormat.getNumberInstance(Locale.GERMANY)
        return try {
            format.format(number?.toDouble())
        } catch (e: Exception) {
            "0"
        }
    }

    fun simpleCovertCurrency(mContext: Context?, amount: String?): String {
        return mContext?.getString(R.string.format_price, convertDoubletoFormatNumber(amount))
            ?: "Rp 0"
    }
}