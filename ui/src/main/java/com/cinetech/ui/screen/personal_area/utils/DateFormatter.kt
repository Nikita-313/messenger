package com.cinetech.ui.screen.personal_area.utils

import com.cinetech.ui.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

const val pattern = "yyyy-MM-dd"

fun millisToDate(millis: Long): String {
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    val date = Date(millis)
    return dateFormat.format(date)
}

fun getZodiacSignRId(date: String?): Int? {
    if (date == null) return null
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("UTC")

    val date = try {
        format.parse(date)
    } catch (e: Exception) {
        return null
    }

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