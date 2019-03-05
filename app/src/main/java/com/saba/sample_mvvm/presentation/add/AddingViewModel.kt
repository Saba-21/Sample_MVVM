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
        postState(AddingViewState.Loading)
        addDisposables(
            getGlobalReposUseCase
                .createObservable(key)
                .subscribe({
                    postState(AddingViewState.DrawRepoList(it))
                }, {
                    postState(AddingViewState.Error(it.message ?: ""))
                })
        )
    }

    fun onSaveClicked(repoModel: RepoModel) {
        postState(AddingViewState.Loading)
        addDisposables(
            saveLocalRepoUseCase
                .createObservable(repoModel)
                .subscribe({

                }, {
                    postState(AddingViewState.Error(it.message ?: ""))
                })
        )
    }

}