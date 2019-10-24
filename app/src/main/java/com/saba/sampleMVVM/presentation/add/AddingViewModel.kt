package com.saba.sampleMVVM.presentation.add

import com.saba.sampleMVVM.base.presentation.BaseViewModel
import com.saba.sampleMVVM.domain.useCases.GetGlobalReposUseCase
import io.reactivex.Observable

class AddingViewModel(
    private val getGlobalReposUseCase: GetGlobalReposUseCase
) : BaseViewModel<AddingViewState, AddingViewActon>() {

    override fun onActionReceived(action: AddingViewActon): Observable<AddingViewState> {
        return when (action) {
            is AddingViewActon.SearchRepos -> {
                getGlobalReposUseCase
                    .createObservable(action.key)
                    .map {
                        AddingViewState.DrawRepoList(it)
                    }
            }
        }
    }

}