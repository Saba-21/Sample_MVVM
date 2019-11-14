package com.saba.sampleMVVM.presentation.add

import com.saba.sampleMVVM.base.presentation.BaseViewAction

sealed class AddingViewActon(
    override val needsNetwork: Boolean = false,
    override val needsLoader: Boolean = false
) : BaseViewAction {

    data class SearchRepos(val key: String) :
        AddingViewActon(needsNetwork = true, needsLoader = true)

}