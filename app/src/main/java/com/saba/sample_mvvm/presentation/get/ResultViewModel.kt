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
        postState(ResultViewState.Loading)
        addDisposables(
            getLocalReposUseCase
                .createObservable()
                .subscribe({
                    postState(ResultViewState.DrawRepoList(it))
                }, {
                    postState(ResultViewState.Error(it.message ?: ""))
                })
        )
    }

    fun onDeleteClicked(repoModel: RepoModel) {
        postState(ResultViewState.Loading)
        addDisposables(
            dropLocalReposUseCase
                .createObservable(repoModel)
                .subscribe({

                }, {
                    postState(ResultViewState.Error(it.message ?: ""))
                })
        )
    }

}