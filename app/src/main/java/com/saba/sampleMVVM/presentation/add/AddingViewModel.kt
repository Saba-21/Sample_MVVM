package com.saba.sampleMVVM.presentation.add

import com.saba.sampleMVVM.base.extensions.makeObservable
import com.saba.sampleMVVM.base.structure.BaseViewModel
import com.saba.sampleMVVM.domain.useCases.GetGlobalReposUseCase
import com.saba.sampleMVVM.domain.useCases.SaveLocalRepoUseCase
import io.reactivex.Observable

class AddingViewModel(
    private val getGlobalReposUseCase: GetGlobalReposUseCase,
    private val saveLocalRepoUseCase: SaveLocalRepoUseCase
) : BaseViewModel<AddingViewState, AddingViewActon>() {

    override fun onActionReceived(action: AddingViewActon): Observable<AddingViewState> {
        return when (action) {

            is AddingViewActon.NavigateToResult -> {
                AddingViewState.NavigateToResult.makeObservable()
            }

            is AddingViewActon.SearchClick -> {
                getGlobalReposUseCase
                    .createObservable(action.key)
                    .map {
                        AddingViewState.DrawRepoList(it) as AddingViewState
                    }.onErrorReturn {
                        AddingViewState.Error(it.message ?: "")
                    }
            }

            is AddingViewActon.SaveClick -> {
                saveLocalRepoUseCase
                    .createObservable(action.repoModel)
                    .map {
                        AddingViewState.ShowItemAdded as AddingViewState
                    }.onErrorReturn {
                        AddingViewState.Error(it.message ?: "")
                    }
            }
        }
    }

}