package com.saba.sampleMVVM.presentation.get

import com.saba.sampleMVVM.base.presentation.BaseViewModel
import com.saba.sampleMVVM.domain.useCases.GetLocalReposUseCase
import io.reactivex.Observable

class ResultViewModel(
    private val getLocalReposUseCase: GetLocalReposUseCase
) : BaseViewModel<ResultViewState, ResultViewAction>() {

    override fun onActionReceived(action: ResultViewAction): Observable<ResultViewState> {
        return when (action) {
            is ResultViewAction.LoadRepos -> {
                getLocalReposUseCase
                    .createObservable()
                    .map {
                        ResultViewState.DrawRepoList(it)
                    }
            }
        }
    }

}