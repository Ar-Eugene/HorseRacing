package com.example.horseracing.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.horseracing.R
import com.example.horseracing.domain.RaceParticipant
import com.example.horseracing.ui.RaceViewModel.Screen
import com.example.horseracing.ui.components.RaceControls
import com.example.horseracing.ui.components.RaceResultsScreen
import com.example.horseracing.ui.components.StatusIndicator
import com.example.horseracing.ui.theme.HorseRacingTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun RaceTrackerApp() {
    val horse1 = stringResource(id = R.string.horse1)
    val horse2 = stringResource(id = R.string.horse2)
    val viewModel: RaceViewModel = koinViewModel { parametersOf(horse1, horse2) }

    when (viewModel.currentScreen) {
        Screen.RACE -> RaceTrackerScreen(
            playerOne = viewModel.playerOne,
            playerTwo = viewModel.playerTwo,
            isRunning = viewModel.raceInProgress,
            onRunStateChange = { if (it) viewModel.startRace() else viewModel.resetRace() },
            onShowResults = { viewModel.showResults() },
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .safeDrawingPadding()
                .padding(horizontal = dimensionResource(R.dimen.padding_16)),
        )

        Screen.RESULTS -> RaceResultsScreen(
            winners = viewModel.winners,
            onBack = { viewModel.showRace() }
        )
    }
}

@Composable
private fun RaceTrackerScreen(
    playerOne: RaceParticipant,
    playerTwo: RaceParticipant,
    isRunning: Boolean,
    onRunStateChange: (Boolean) -> Unit,
    onShowResults: () -> Unit,
    modifier: Modifier = Modifier
) {
    var wasStarted by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.run_a_race),
            style = MaterialTheme.typography.headlineSmall,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_16)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(R.drawable.horse_ic),
                contentDescription = null,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_16)),
            )

            StatusIndicator(
                participantName = playerOne.name,
                currentProgress = playerOne.currentProgress,
                maxProgress = stringResource(R.string.progress_percentage, playerOne.maxProgress),
                progressFactor = playerOne.progressFactor,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_24)))

            StatusIndicator(
                participantName = playerTwo.name,
                currentProgress = playerTwo.currentProgress,
                maxProgress = stringResource(R.string.progress_percentage, playerTwo.maxProgress),
                progressFactor = playerTwo.progressFactor,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_24)))

            RaceControls(
                isRunning = isRunning,
                onRunStateChange = {
                    wasStarted = true
                    onRunStateChange(it)
                },
                modifier = Modifier.fillMaxWidth(),
                wasStarted = wasStarted
            )

            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_24)))

            Button(onClick = onShowResults, modifier = Modifier.fillMaxWidth()) {
                Text("Результаты гонок")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RaceTrackerAppPreview() {
    HorseRacingTheme {
        RaceTrackerApp()
    }
}