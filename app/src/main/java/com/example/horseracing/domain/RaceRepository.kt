package com.example.horseracing.domain

// Интерфейс репозитория для результатов гонок (DIP)
interface RaceRepository {
    fun addWinner(winner: String)
    fun getWinners(): List<String>
    fun clearWinners()
}

// Конкретная реализация репозитория
class RaceRepositoryImpl : RaceRepository {
    private val winners = mutableListOf<String>()
    
    override fun addWinner(winner: String) {
        winners.add(winner)
    }
    
    override fun getWinners(): List<String> {
        return winners.toList()
    }
    
    override fun clearWinners() {
        winners.clear()
    }
} 