package com.saba.sampleMVVM.presentation.get

import com.saba.sampleMVVM.base.presentation.BaseViewModel
import com.saba.sampleMVVM.domain.useCases.GetLocalReposUseCase

class ResultViewModel(
    private val getLocalReposUseCase: GetLocalReposUseCase
) : BaseViewModel<ResultViewState, ResultViewAction>() {

    override suspend fun onActionReceived(action: ResultViewAction): ResultViewState {
        return when (action) {
            is ResultViewAction.LoadRepos -> {
                ResultViewState.DrawRepoList(getLocalReposUseCase.createObservable())
            }
        }
    }

}