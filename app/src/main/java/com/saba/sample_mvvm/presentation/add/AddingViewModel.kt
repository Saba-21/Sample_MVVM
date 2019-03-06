package com.saba.sample_mvvm.presentation.add

import com.saba.sample_mvvm.base.structure.BaseViewModel
import com.saba.sample_mvvm.domain.dataModels.apiModels.RepoModel
import com.saba.sample_mvvm.domain.useCases.GetGlobalReposUseCase
import com.saba.sample_mvvm.domain.useCases.SaveLocalRepoUseCase

class AddingViewModel(
    private val getGlobalReposUseCase: GetGlobalReposUseCase,
    private val saveLocalRepoUseCase: SaveLocalRepoUseCase
) : BaseViewModel<AddingViewState>() {

    fun onSearchClicked(key: String) {
        postState(AddingViewState.ShowLoading)
        addDisposables(
            getGlobalReposUseCase
                .createObservable(key)
                .map {
                    AddingViewState.DrawRepoList(it) as AddingViewState
                }.onErrorReturn {
                    AddingViewState.Error(it.message ?: "")
                }.subscribe {
                    postState(it)
                    postState(AddingViewState.HideLoading)
                }
        )
    }

    fun onSaveClicked(repoModel: RepoModel) {
        postState(AddingViewState.ShowLoading)
        addDisposables(
            saveLocalRepoUseCase
                .createObservable(repoModel)
                .map {
                    AddingViewState.ShowItemAdded as AddingViewState
                }.onErrorReturn {
                    AddingViewState.Error(it.message ?: "")
                }.subscribe {
                    postState(it)
                    postState(AddingViewState.HideLoading)
                }
        )
    }

    fun onNavigateToResult() {
        postState(AddingViewState.NavigateToResult)
    }

}