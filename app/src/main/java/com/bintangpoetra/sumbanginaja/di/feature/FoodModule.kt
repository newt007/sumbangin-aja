package com.bintangpoetra.sumbanginaja.di.feature

import com.bintangpoetra.sumbanginaja.data.food.FoodDataStore
import com.bintangpoetra.sumbanginaja.data.food.FoodRepository
import com.bintangpoetra.sumbanginaja.domain.food.FoodInteractor
import com.bintangpoetra.sumbanginaja.domain.food.FoodUseCase
import com.bintangpoetra.sumbanginaja.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val foodModule = module {
    factory<FoodUseCase> { FoodInteractor(get()) }
    factory<FoodRepository> { FoodDataStore(get()) }

    single { FoodDataStore(get()) }
    single { FoodInteractor(get()) }

    viewModel {
        HomeViewModel(get())
    }
}