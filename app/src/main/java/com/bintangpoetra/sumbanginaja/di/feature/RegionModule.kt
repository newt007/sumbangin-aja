package com.bintangpoetra.sumbanginaja.di.feature

import com.bintangpoetra.sumbanginaja.data.region.RegionDataStore
import com.bintangpoetra.sumbanginaja.data.region.RegionRepository
import com.bintangpoetra.sumbanginaja.domain.region.RegionInteractor
import com.bintangpoetra.sumbanginaja.domain.region.RegionUseCase
import com.bintangpoetra.sumbanginaja.presentation.region.city.CityViewModel
import com.bintangpoetra.sumbanginaja.presentation.region.province.ProvinceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val regionModule = module {

    factory<RegionUseCase> { RegionInteractor(get()) }
    factory<RegionRepository> { RegionDataStore(get()) }

    single { RegionDataStore(get()) }
    single { RegionInteractor(get()) }

    viewModel { ProvinceViewModel(get()) }
    viewModel { CityViewModel(get()) }

}