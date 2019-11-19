package com.saba.sampleMVVM.app.modules

import com.saba.sampleMVVM.presentation.add.AddingViewModel
import com.saba.sampleMVVM.presentation.get.ResultViewModel
import com.saba.sampleMVVM.presentation.main.MainViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

object ViewModelModule {

    val module = module {

        viewModel {
            AddingViewModel(get())
        }

        viewModel {
            ResultViewModel(get())
        }

        viewModel {
            MainViewModel(get(), get())
        }

    }

}