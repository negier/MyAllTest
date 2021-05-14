package com.xuebinduan.koin

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModules = module {
    viewModel { MyViewModel() }
}

val objectModules = module {
    factory { User() }
    single { Data() }
}