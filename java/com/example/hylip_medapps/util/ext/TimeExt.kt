package com.windranger.reminder.util.ext

import android.text.format.DateUtils
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by didik on 07/12/17.
 */

const val BATIC_FULL_FORMAT = "EEE, dd MMM - HH:mm"
const val BATIC_SIMPLE_FORMAT = "EEE, dd MMM"
const val DATE_FORMAT = "yyyy-MM-dd"
const val TIME_FORMAT_FULL = "HH:mm:ss"
const val TIME_FORMAT = "HH:mm"
const val HUMAN_FORMAT_FULL = "dd MMMM yyyy - HH:mm"

fun getDateNow(format: String = "MMMM d"): String {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.format(Date())
}

fun stringToLong(date: String): Long {
    val dateFormat = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    var convertedDate = Date()
    try {
        convertedDate = dateFormat.parse(date)
    } catch (e: ParseException) {
        // TODO Auto-generated catch block
        e.printStackTrace()
    }

    return convertedDate.time
}

fun dateFormatter(date: String, formatType: String): String {
    val convertedDate = Date(stringToLong(date))

    val format = SimpleDateFormat(formatType, Locale.getDefault())

    return format.format(convertedDate)
}

fun dateFormatter(date: Long, formatType: String): String {
    val convertedDate = Date(date)

    val format = SimpleDateFormat(formatType, Locale.getDefault())

    return format.format(convertedDate)
}

fun toRelative(date: String?): String {
    if (date != null) {
        val now = System.currentTimeMillis()
        val time = stringToLong(date)

        return DateUtils.getRelativeTimeSpanString(time, now, DateUtils.SECOND_IN_MILLIS) as String
    }

    return ""
}

fun toNumberFormat(number: String): String {
    val lng = number.toLong()
    val decimalFormat = DecimalFormat("#,###")
    /*val symbols = DecimalFormatSymbols(CJTime.locale)
    symbols.setGroupingSeparator('.')
    decimalFormat.setDecimalFormatSymbols(symbols)*/

    return decimalFormat.format(lng)
}