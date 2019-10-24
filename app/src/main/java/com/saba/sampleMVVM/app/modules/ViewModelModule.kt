package com.saba.sampleMVVM.app.modules

import com.saba.sampleMVVM.presentation.add.AddingViewModel
import com.saba.sampleMVVM.presentation.get.ResultViewModel
import com.saba.sampleMVVM.presentation.main.MainViewModel
import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module

object ViewModelModule {
    
    val module = module {
        
        viewModel<AddingViewModel>()

        viewModel<ResultViewModel>()

        viewModel<MainViewModel>()
        
    }
    
}