package com.bintangpoetra.sumbanginaja.di

import com.bintangpoetra.sumbanginaja.utils.PreferenceManager
import org.koin.dsl.module

val preferenceModule = module {

    single { PreferenceManager(get()) }

}