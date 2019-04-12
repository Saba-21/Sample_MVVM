package com.saba.sampleMVVM.presentation.get

import com.saba.sampleMVVM.base.extensions.makeObservable
import com.saba.sampleMVVM.base.structure.BaseViewModel
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
                postState(ResultViewState.ShowLoading)
                getLocalReposUseCase
                    .createObservable()
                    .map {
                        ResultViewState.DrawRepoList(it) as ResultViewState
                    }.onErrorReturn {
                        ResultViewState.Error(it.message ?: "")
                    }.map {
                        postState(ResultViewState.HideLoading)
                        it
                    }
            }

            is ResultViewAction.DropClicked -> {
                dropLocalReposUseCase
                    .createObservable(action.repoModel)
                    .map {
                        ResultViewState.ShowItemDropped as ResultViewState
                    }.onErrorReturn {
                        ResultViewState.Error(it.message ?: "")
                    }
            }
        }
    }

}