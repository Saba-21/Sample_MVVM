package com.saba.sampleMVVM.presentation.main

import com.saba.sampleMVVM.base.presentation.BaseViewModel
import com.saba.sampleMVVM.domain.useCases.DropLocalReposUseCase
import com.saba.sampleMVVM.domain.useCases.SaveLocalRepoUseCase
import io.reactivex.Observable

class MainViewModel(
    private val saveLocalRepoUseCase: SaveLocalRepoUseCase,
    private val dropLocalReposUseCase: DropLocalReposUseCase
) : BaseViewModel<MainViewState, MainViewAction>() {

    override fun onActionReceived(action: MainViewAction): Observable<MainViewState> {
        return when (action) {
            is MainViewAction.DropClicked -> {
                dropLocalReposUseCase
                    .createObservable(action.repoModel)
                    .map {
                        MainViewState.ShowItemDropped
                    }
            }
            is MainViewAction.SaveClick -> {
                saveLocalRepoUseCase
                    .createObservable(action.repoModel)
                    .map {
                        MainViewState.ShowItemAdded
                    }
            }
        }
    }

}