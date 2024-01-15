package com.example.sevenwindstest.di

import com.example.sevenwindstest.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {

    viewModel {
        MainViewModel(
            userRegistrationUseCase = get(),
            userAuthUseCase = get(),
            getNearestCoffeeShopsUseCase = get(),
            getMyLocationUseCase = get(),
            getMenuUseCase = get()
        )
    }
}