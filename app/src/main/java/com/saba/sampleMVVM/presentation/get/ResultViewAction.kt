package com.saba.sampleMVVM.presentation.get

import com.saba.sampleMVVM.base.presentation.BaseViewAction
import com.saba.sampleMVVM.domain.models.apiModels.RepoModel

sealed class ResultViewAction(
    override val needsNetwork: Boolean = false,
    override val needsLoader: Boolean = false
) : BaseViewAction {

    object LoadRepos : ResultViewAction()
    object NavigateToResult : ResultViewAction()
    data class DropClicked(val repoModel: RepoModel) : ResultViewAction()

}