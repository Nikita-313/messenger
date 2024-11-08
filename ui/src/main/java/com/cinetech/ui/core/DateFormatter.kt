package com.cinetech.ui.core

import com.cinetech.ui.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

const val ISO8601Patter = "yyyy-MM-dd'T'HH:mm:ssXXX"

fun formatMillisToISO8601(millis: Long): String {
    val date = Date(millis)
    val sdf = SimpleDateFormat(ISO8601Patter, Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    return sdf.format(date)
}

fun iso8601ToDateString(iso8601String: String, outputPattern: String): String? {
    val inputFormat = SimpleDateFormat(ISO8601Patter, Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    return try {
        val date = inputFormat.parse(iso8601String)
        val outputFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
        outputFormat.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun getZodiacSignRId(iso8601String: String?): Int? {
    if (iso8601String == null) return null
    val format = SimpleDateFormat(ISO8601Patter, Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("UTC")

    val date = format.parse(iso8601String)

    val calendar = Calendar.getInstance()
    calendar.time = date

    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.get(Calendar.MONTH) + 1

    return when (month) {
        1 -> if (day <= 19) R.string.capricorn else R.string.aquarius
        2 -> if (day <= 18) R.string.aquarius else R.string.pisces
        3 -> if (day >= 21) R.string.aries else R.string.pisces
        4 -> if (day <= 19) R.string.aries else R.string.taurus
        5 -> if (day <= 20) R.string.taurus else R.string.gemini
        6 -> if (day <= 20) R.string.gemini else R.string.cancer
        7 -> if (day <= 22) R.string.cancer else R.string.leo
        8 -> if (day <= 22) R.string.leo else R.string.virgo
        9 -> if (day <= 22) R.string.virgo else R.string.libra
        10 -> if (day <= 22) R.string.libra else R.string.scorpio
        11 -> if (day <= 21) R.string.scorpio else R.string.sagittarius
        12 -> if (day <= 21) R.string.sagittarius else R.string.capricorn
        else -> null
    }
}