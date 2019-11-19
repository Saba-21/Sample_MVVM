package com.saba.sampleMVVM.presentation.add

import com.saba.sampleMVVM.base.presentation.BaseViewModel
import com.saba.sampleMVVM.domain.useCases.GetGlobalReposUseCase

class AddingViewModel(
    private val getGlobalReposUseCase: GetGlobalReposUseCase
) : BaseViewModel<AddingViewState, AddingViewActon>() {

    override suspend fun onActionReceived(action: AddingViewActon): AddingViewState {
        return when (action) {
            is AddingViewActon.SearchRepos -> {
                AddingViewState.DrawRepoList(getGlobalReposUseCase.createObservable(action.key))
            }
        }
    }

}