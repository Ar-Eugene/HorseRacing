package com.example.horseracing.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlin.random.Random

// Интерфейс для участников гонки
interface RaceParticipant {
    val name: String
    val currentProgress: Int
    val maxProgress: Int
    val progressFactor: Float
    
    suspend fun run()
    fun reset()
}

// Конкретная реализация участника гонки
class HorseParticipant(
    name: String,
    maxProgress: Int = 100,
    private val progressDelayMillis: Long = 100L,
    private val progressIncrement: Int = 1,
    private val initialProgress: Int = 0
) : RaceParticipant {
    
    init {
        require(maxProgress > 0) { "maxProgress=$maxProgress; must be > 0" }
        require(progressIncrement > 0) { "progressIncrement=$progressIncrement; must be > 0" }
    }
    
    override val name: String = name
    override val maxProgress: Int = maxProgress
    
    private var _currentProgress by mutableStateOf(initialProgress)
    override val currentProgress: Int
        get() = _currentProgress
    
    override val progressFactor: Float
        get() = currentProgress / maxProgress.toFloat()

    override suspend fun run() {
        while (_currentProgress < maxProgress) {
            delay(progressDelayMillis)
            _currentProgress += Random.nextInt(1, 6)
        }
    }

    override fun reset() {
        _currentProgress = 0
    }
}