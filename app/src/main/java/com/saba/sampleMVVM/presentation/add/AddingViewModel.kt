package com.saba.sampleMVVM.presentation.add

import com.saba.sampleMVVM.custom.extensions.makeObservable
import com.saba.sampleMVVM.base.presentation.BaseViewModel
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
                        AddingViewState.DrawRepoList(it)
                    }
            }
            is AddingViewActon.SaveClick -> {
                saveLocalRepoUseCase
                    .createObservable(action.repoModel)
                    .map {
                        AddingViewState.ShowItemAdded
                    }
            }
        }
    }

}