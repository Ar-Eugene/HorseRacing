package com.example.horseracing.domain

import com.example.horseracing.utils.TextFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Интерфейс для сервиса гонки
interface RaceService {
    fun startRace(
        scope: CoroutineScope,
        participants: List<RaceParticipant>,
        onWinner: (String) -> Unit,
        onRaceComplete: () -> Unit
    ): Job

    fun stopRace()
}

// Конкретная реализация сервиса гонки
class RaceServiceImpl : RaceService {

    private var raceJob: Job? = null

    override fun startRace(
        scope: CoroutineScope,
        participants: List<RaceParticipant>,
        onWinner: (String) -> Unit,
        onRaceComplete: () -> Unit
    ): Job {
        raceJob?.cancel()

        raceJob = scope.launch {
            val participantJobs = participants.map { participant ->
                launch { participant.run() }
            }

            var winnerRecorded = false
            var winnerCount = 0

            while (!winnerRecorded) {
                participants.forEach { participant ->
                    if (participant.currentProgress >= participant.maxProgress && !winnerRecorded) {
                        winnerCount++
                        val winnerMessage =
                            TextFormatter.formatWinnerMessage(winnerCount, participant.name)
                        onWinner(winnerMessage)
                        winnerRecorded = true
                    }
                }
                delay(50)
            }

            participantJobs.forEach { it.join() }
            participants.forEach { it.reset() }
            onRaceComplete()
        }

        return raceJob!!
    }

    override fun stopRace() {
        raceJob?.cancel()
        raceJob = null
    }
} 