package com.saba.sample_mvvm.presentation.main


sealed class MainViewState {

    data class Error(val message: String) : MainViewState()
    object Loading : MainViewState()
    object Done : MainViewState()

}