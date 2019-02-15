package com.saba.sample_mvvm.presentation.add

import com.saba.sample_mvvm.domain.dataModels.apiModels.RepoModel

sealed class AddingViewState {

    data class Error(val message: String) : AddingViewState()
    object Loading : AddingViewState()
    data class DrawRepoList(val repoList: List<RepoModel>) : AddingViewState()
    object DrawSaveItem : AddingViewState()

}