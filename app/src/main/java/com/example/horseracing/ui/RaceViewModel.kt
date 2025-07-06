package com.example.horseracing.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.horseracing.domain.HorseParticipant
import com.example.horseracing.domain.RaceParticipant
import com.example.horseracing.domain.RaceRepository
import com.example.horseracing.domain.RaceService

class RaceViewModel(
    private val raceService: RaceService,
    private val raceRepository: RaceRepository,
    horse1Name: String = "Пегас",
    horse2Name: String = "Слэвин"
) : ViewModel() {
    enum class Screen { RACE, RESULTS }

    var playerOne by mutableStateOf(HorseParticipant(name = horse1Name))
        private set
    var playerTwo by mutableStateOf(HorseParticipant(name = horse2Name))
        private set
    var raceInProgress by mutableStateOf(false)
        private set
    var winners by mutableStateOf(raceRepository.getWinners())
        private set
    var currentScreen by mutableStateOf(Screen.RACE)
        private set

    private val participants: List<RaceParticipant>
        get() = listOf(playerOne, playerTwo)

    fun startRace() {
        if (raceInProgress) return

        raceInProgress = true
        raceService.startRace(
            scope = viewModelScope,
            participants = participants,
            onWinner = { winner ->
                raceRepository.addWinner(winner)
                winners = raceRepository.getWinners()
            },
            onRaceComplete = {
                raceInProgress = false
            }
        )
    }

    fun resetRace() {
        raceService.stopRace()
        playerOne.reset()
        playerTwo.reset()
        raceInProgress = false
    }

    fun showResults() {
        currentScreen = Screen.RESULTS
    }

    fun showRace() {
        currentScreen = Screen.RACE
    }
} 