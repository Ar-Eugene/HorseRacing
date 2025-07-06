package com.example.horseracing.utils

object TextFormatter {
    fun formatOrdinal(number: Int): String = when (number) {
        1 -> "первой"
        2 -> "второй"
        3 -> "третьей"
        4 -> "четвертой"
        5 -> "пятой"
        6 -> "шестой"
        7 -> "седьмой"
        8 -> "восьмой"
        9 -> "девятой"
        10 -> "десятой"
        else -> "$number-й"
    }
    
    fun formatWinnerMessage(winnerNumber: Int, participantName: String): String {
        return "Победитель ${formatOrdinal(winnerNumber)} гонки — $participantName"
    }
} 