package com.saba.sample_mvvm.presentation.add

import com.saba.sample_mvvm.base.extensions.toLiveData
import com.saba.sample_mvvm.base.structure.BaseViewModel
import com.saba.sample_mvvm.domain.dataModels.apiModels.RepoModel
import com.saba.sample_mvvm.domain.useCases.GetGlobalReposUseCase
import com.saba.sample_mvvm.domain.useCases.SaveLocalRepoUseCase
import io.reactivex.subjects.BehaviorSubject

class AddingViewModel(
    private val getGlobalReposUseCase: GetGlobalReposUseCase,
    private val saveLocalRepoUseCase: SaveLocalRepoUseCase
) : BaseViewModel() {

    private val onSearchResultReceived = BehaviorSubject.create<List<RepoModel>>()

    fun onSearchResultReceived() = onSearchResultReceived.toLiveData()

    fun onSearchClicked(key: String) {
        addDisposables(
            getGlobalReposUseCase
                .createObservable(key)
                .subscribe({
                    onSearchResultReceived.onNext(it)
                }, {
                    onSearchResultReceived.onNext(emptyList())
                })
        )
    }

    fun onSaveClicked(repoModel: RepoModel) {
        addDisposables(
            saveLocalRepoUseCase
                .createObservable(repoModel)
                .subscribe({

                }, {

                })
        )
    }

}