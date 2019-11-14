package com.saba.sampleMVVM.presentation.add

import com.saba.sampleMVVM.base.presentation.BaseViewModel
import com.saba.sampleMVVM.domain.useCases.GetGlobalReposUseCase
import io.reactivex.Observable

class AddingViewModel(
    private val getGlobalReposUseCase: GetGlobalReposUseCase
) : BaseViewModel<AddingViewState, AddingViewActon>() {

    override fun onActionReceived(action: AddingViewActon): AddingViewState {
        return when (action) {
            is AddingViewActon.SearchRepos -> {
                AddingViewState.DrawRepoList(emptyList())
            }
        }
    }

}