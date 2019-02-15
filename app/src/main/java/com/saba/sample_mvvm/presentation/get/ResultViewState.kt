package com.saba.sample_mvvm.presentation.get

import com.saba.sample_mvvm.domain.dataModels.apiModels.RepoModel

sealed class ResultViewState {

    data class Error(val message: String) : ResultViewState()
    object Loading : ResultViewState()
    data class DrawRepoList(val repoList: List<RepoModel>) : ResultViewState()
    object DrawDropItem : ResultViewState()

}