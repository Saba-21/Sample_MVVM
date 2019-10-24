package com.saba.sampleMVVM.presentation.get

import com.saba.sampleMVVM.base.presentation.BaseViewAction

sealed class ResultViewAction(
    override val needsNetwork: Boolean = false,
    override val needsLoader: Boolean = false
) : BaseViewAction {

    object LoadRepos : ResultViewAction()

}