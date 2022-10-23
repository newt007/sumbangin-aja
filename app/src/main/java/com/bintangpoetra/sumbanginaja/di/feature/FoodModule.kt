package com.bintangpoetra.sumbanginaja.di.feature

import com.bintangpoetra.sumbanginaja.data.food.FoodDataStore
import com.bintangpoetra.sumbanginaja.data.food.FoodRepository
import com.bintangpoetra.sumbanginaja.domain.food.FoodInteractor
import com.bintangpoetra.sumbanginaja.domain.food.FoodUseCase
import com.bintangpoetra.sumbanginaja.presentation.auth.register.RegisterViewModel
import com.bintangpoetra.sumbanginaja.presentation.food.FoodDetailViewModel
import com.bintangpoetra.sumbanginaja.presentation.food.list.FoodListViewModel
import com.bintangpoetra.sumbanginaja.presentation.food.add.AddFoodViewModel
import com.bintangpoetra.sumbanginaja.presentation.home.HomeViewModel
import com.bintangpoetra.sumbanginaja.presentation.scanner.ScannerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val foodModule = module {

    factory<FoodUseCase> { FoodInteractor(get()) }
    factory<FoodRepository> { FoodDataStore(get(), get()) }

    single { FoodDataStore(get(), get()) }
    single { FoodInteractor(get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { FoodDetailViewModel(get()) }
    viewModel { AddFoodViewModel(get()) }
    viewModel { FoodListViewModel(get()) }
    viewModel { ScannerViewModel(get()) }

}