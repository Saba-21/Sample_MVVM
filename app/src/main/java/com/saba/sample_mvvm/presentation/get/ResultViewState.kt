package com.saba.sample_mvvm.presentation.get

import com.saba.sample_mvvm.base.structure.BaseViewState
import com.saba.sample_mvvm.domain.dataModels.apiModels.RepoModel

sealed class ResultViewState(override val isStateAware: Boolean = false) : BaseViewState {

    data class DrawRepoList(val repoList: List<RepoModel>, override val isStateAware: Boolean = true) : ResultViewState()
    data class Error(val message: String) : ResultViewState()
    object ShowLoading : ResultViewState()
    object HideLoading: ResultViewState()
    object ShowItemDropped: ResultViewState()
    object NavigateToAdding: ResultViewState()

}