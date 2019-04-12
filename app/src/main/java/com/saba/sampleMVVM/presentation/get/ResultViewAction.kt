package com.saba.sampleMVVM.presentation.get

import com.saba.sampleMVVM.base.structure.BaseViewAction
import com.saba.sampleMVVM.domain.models.apiModels.RepoModel

sealed class ResultViewAction : BaseViewAction {

    object LoadRepos : ResultViewAction()
    object NavigateToResult : ResultViewAction()
    data class DropClicked(val repoModel: RepoModel) : ResultViewAction()

}