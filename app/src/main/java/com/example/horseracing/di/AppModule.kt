package com.example.horseracing.di

import com.example.horseracing.domain.RaceRepository
import com.example.horseracing.domain.RaceRepositoryImpl
import com.example.horseracing.domain.RaceService
import com.example.horseracing.domain.RaceServiceImpl
import com.example.horseracing.ui.RaceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Репозиторий результатов
    single<RaceRepository> { RaceRepositoryImpl() }

    // Сервис гонки
    single<RaceService> { RaceServiceImpl() }

    // ViewModel с зависимостями
    viewModel { (horse1: String, horse2: String) ->
        RaceViewModel(
            raceService = get(),
            raceRepository = get(),
            horse1Name = horse1,
            horse2Name = horse2
        )
    }
} 