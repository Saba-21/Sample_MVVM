package com.saba.sample_mvvm.presentation.get

import com.saba.sample_mvvm.base.extensions.toLiveData
import com.saba.sample_mvvm.base.structure.BaseViewModel
import com.saba.sample_mvvm.domain.dataModels.apiModels.RepoModel
import com.saba.sample_mvvm.domain.useCases.DropLocalReposUseCase
import com.saba.sample_mvvm.domain.useCases.GetLocalReposUseCase

class ResultViewModel(
    private val getLocalReposUseCase: GetLocalReposUseCase,
    private val dropLocalReposUseCase: DropLocalReposUseCase
) : BaseViewModel() {

    fun onLocalReposReceived() = getLocalReposUseCase.createObservable().toLiveData()

    fun onDeleteClicked(repoModel: RepoModel) {
        addDisposables(
            dropLocalReposUseCase
                .createObservable(repoModel)
                .subscribe({

                }, {

                })
        )
    }

}