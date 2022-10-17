package com.bintangpoetra.sumbanginaja.di.feature

import com.bintangpoetra.sumbanginaja.data.auth.AuthDataStore
import com.bintangpoetra.sumbanginaja.data.auth.AuthRepository
import com.bintangpoetra.sumbanginaja.domain.auth.AuthInteractor
import com.bintangpoetra.sumbanginaja.domain.auth.AuthUseCase
import com.bintangpoetra.sumbanginaja.presentation.auth.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {

    factory<AuthUseCase> { AuthInteractor(get()) }
    factory<AuthRepository> { AuthDataStore(get()) }

    single { AuthDataStore(get()) }
    single { AuthInteractor(get()) }

    viewModel { LoginViewModel(get()) }
}
