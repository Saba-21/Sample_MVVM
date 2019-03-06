package com.saba.sample_mvvm.presentation.get

import com.saba.sample_mvvm.base.structure.BaseViewModel
import com.saba.sample_mvvm.domain.dataModels.apiModels.RepoModel
import com.saba.sample_mvvm.domain.useCases.DropLocalReposUseCase
import com.saba.sample_mvvm.domain.useCases.GetLocalReposUseCase

class ResultViewModel(
    getLocalReposUseCase: GetLocalReposUseCase,
    private val dropLocalReposUseCase: DropLocalReposUseCase
) : BaseViewModel<ResultViewState>() {

    init {
        postState(ResultViewState.ShowLoading)
        addDisposables(
            getLocalReposUseCase
                .createObservable()
                .map {
                    ResultViewState.DrawRepoList(it) as ResultViewState
                }.onErrorReturn {
                    ResultViewState.Error(it.message ?: "")
                }.subscribe {
                    postState(it)
                    postState(ResultViewState.HideLoading)
                }
        )
    }

    fun onDeleteClicked(repoModel: RepoModel) {
        postState(ResultViewState.ShowLoading)
        addDisposables(
            dropLocalReposUseCase
                .createObservable(repoModel)
                .map {
                    ResultViewState.ShowItemDropped as ResultViewState
                }.onErrorReturn {
                    ResultViewState.Error(it.message ?: "")
                }.subscribe {
                    postState(it)
                    postState(ResultViewState.HideLoading)
                }
        )
    }

    fun onNavigateToAdding() {
        postState(ResultViewState.NavigateToAdding)
    }

}