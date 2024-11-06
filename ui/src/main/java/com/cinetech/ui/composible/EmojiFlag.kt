package com.cinetech.ui.composible

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit

@Composable
fun EmojiFlag(countryCode: String?,fontSize: TextUnit = TextUnit.Unspecified) {
    if (countryCode == null) return
    val flagEmoji = countryCode
        .uppercase()
        .map { char -> 0x1F1E6 - 'A'.code + char.code }
        .map { Character.toChars(it) }
        .joinToString("") { String(it) }

    Text(flagEmoji, fontSize = fontSize)
}