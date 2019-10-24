package com.saba.sampleMVVM.presentation.get

import com.saba.sampleMVVM.custom.extensions.makeObservable
import com.saba.sampleMVVM.base.presentation.BaseViewModel
import com.saba.sampleMVVM.domain.useCases.DropLocalReposUseCase
import com.saba.sampleMVVM.domain.useCases.GetLocalReposUseCase
import io.reactivex.Observable

class ResultViewModel(
    private val getLocalReposUseCase: GetLocalReposUseCase,
    private val dropLocalReposUseCase: DropLocalReposUseCase
) : BaseViewModel<ResultViewState, ResultViewAction>() {

    override fun onActionReceived(action: ResultViewAction): Observable<ResultViewState> {
        return when (action) {
            is ResultViewAction.NavigateToResult -> {
                ResultViewState.NavigateToAdding.makeObservable()
            }
            is ResultViewAction.LoadRepos -> {
                getLocalReposUseCase
                    .createObservable()
                    .map {
                        ResultViewState.DrawRepoList(it)
                    }
            }
            is ResultViewAction.DropClicked -> {
                dropLocalReposUseCase
                    .createObservable(action.repoModel)
                    .map {
                        ResultViewState.ShowItemDropped
                    }
            }
        }
    }

}