package com.saba.sampleMVVM.presentation.main

import com.saba.sampleMVVM.base.presentation.BaseViewModel
import com.saba.sampleMVVM.domain.useCases.DropLocalReposUseCase
import com.saba.sampleMVVM.domain.useCases.SaveLocalRepoUseCase
import io.reactivex.Observable

class MainViewModel(
    private val saveLocalRepoUseCase: SaveLocalRepoUseCase,
    private val dropLocalReposUseCase: DropLocalReposUseCase
) : BaseViewModel<MainViewState, MainViewAction>() {

    override fun onActionReceived(action: MainViewAction): MainViewState {
        return when (action) {
            is MainViewAction.DropClicked -> {
                MainViewState.ShowItemDropped
            }
            is MainViewAction.SaveClick -> {
                MainViewState.ShowItemAdded
            }
        }
    }

}