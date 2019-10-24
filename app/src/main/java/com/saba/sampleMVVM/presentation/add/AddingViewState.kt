package com.saba.sampleMVVM.presentation.add

import com.saba.sampleMVVM.base.presentation.BaseViewState
import com.saba.sampleMVVM.domain.models.apiModels.RepoModel

sealed class AddingViewState(override val isStateAware: Boolean = false): BaseViewState {

    data class DrawRepoList(val repoList: List<RepoModel>, override val isStateAware: Boolean = true) : AddingViewState()

}