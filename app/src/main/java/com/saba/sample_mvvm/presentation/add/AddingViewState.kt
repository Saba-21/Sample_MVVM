package com.saba.sample_mvvm.presentation.add

import com.saba.sample_mvvm.base.structure.BaseViewState
import com.saba.sample_mvvm.domain.dataModels.apiModels.RepoModel

sealed class AddingViewState(override val isStateAware: Boolean = false): BaseViewState {

    data class DrawRepoList(val repoList: List<RepoModel>, override val isStateAware: Boolean = true) : AddingViewState()
    data class Error(val message: String) : AddingViewState()
    object Loading : AddingViewState()

}