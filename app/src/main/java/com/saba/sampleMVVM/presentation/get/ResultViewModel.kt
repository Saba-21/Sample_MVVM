package com.saba.sampleMVVM.presentation.get

import com.saba.sampleMVVM.base.structure.BaseViewModel
import com.saba.sampleMVVM.domain.models.apiModels.RepoModel
import com.saba.sampleMVVM.domain.useCases.DropLocalReposUseCase
import com.saba.sampleMVVM.domain.useCases.GetLocalReposUseCase

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