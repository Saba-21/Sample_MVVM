package com.saba.sampleMVVM.presentation.main

import com.saba.sampleMVVM.base.presentation.BaseViewModel
import com.saba.sampleMVVM.domain.useCases.DropLocalReposUseCase
import com.saba.sampleMVVM.domain.useCases.SaveLocalRepoUseCase

class MainViewModel(
    private val saveLocalRepoUseCase: SaveLocalRepoUseCase,
    private val dropLocalReposUseCase: DropLocalReposUseCase
) : BaseViewModel<MainViewState, MainViewAction>() {

    override suspend fun onActionReceived(action: MainViewAction): MainViewState {
        return when (action) {
            is MainViewAction.DropClicked -> {
                dropLocalReposUseCase.createObservable(action.repoModel)
                MainViewState.ShowItemDropped
            }
            is MainViewAction.SaveClick -> {
                saveLocalRepoUseCase.createObservable(action.repoModel)
                MainViewState.ShowItemAdded
            }
        }
    }

}