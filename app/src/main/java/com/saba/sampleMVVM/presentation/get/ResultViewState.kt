package com.saba.sampleMVVM.presentation.get

import com.saba.sampleMVVM.base.presentation.BaseViewState
import com.saba.sampleMVVM.domain.models.RepoModel

sealed class ResultViewState(override val isStateAware: Boolean = false) : BaseViewState {

    data class DrawRepoList(val repoList: List<RepoModel>) : ResultViewState(isStateAware = true)

}