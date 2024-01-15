package com.example.domain.di

import com.example.domain.usecase.GetMenuUseCase
import com.example.domain.usecase.GetMyLocationUseCase
import com.example.domain.usecase.GetNearestCoffeeShopsUseCase
import com.example.domain.usecase.UserAuthUseCase
import com.example.domain.usecase.UserRegistrationUseCase
import org.koin.dsl.module

val domainModule = module {



    factory<UserRegistrationUseCase> {
        UserRegistrationUseCase(loginRepo = get())
    }

    factory<UserAuthUseCase> {
        UserAuthUseCase(loginRepo = get())
    }

    factory<GetNearestCoffeeShopsUseCase> {
        GetNearestCoffeeShopsUseCase(locationRepo = get())
    }

    factory<GetMyLocationUseCase> {
        GetMyLocationUseCase(locationRepo = get())
    }

    factory<GetMenuUseCase> {
        GetMenuUseCase(orderRepo = get())
    }
}