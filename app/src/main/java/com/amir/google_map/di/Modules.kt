package com.amir.google_map.di

import android.app.Application
import com.amir.google_map.ui.features.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.koinApplication
import org.koin.dsl.module


val myModules = module {

viewModel{ HomeViewModel(androidContext() ) }

}