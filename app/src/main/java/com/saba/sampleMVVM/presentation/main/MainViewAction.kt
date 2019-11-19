package com.saba.sampleMVVM.presentation.main

import com.saba.sampleMVVM.base.presentation.BaseViewAction
import com.saba.sampleMVVM.domain.models.RepoModel

sealed class MainViewAction(
    override val needsNetwork: Boolean = false,
    override val needsLoader: Boolean = false
) : BaseViewAction{

    data class SaveClick(val repoModel: RepoModel) : MainViewAction()
    data class DropClicked(val repoModel: RepoModel) : MainViewAction()

}