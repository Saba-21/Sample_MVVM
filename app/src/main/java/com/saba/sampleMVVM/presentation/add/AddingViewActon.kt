package com.saba.sampleMVVM.presentation.add

import com.saba.sampleMVVM.base.structure.BaseViewAction
import com.saba.sampleMVVM.domain.models.apiModels.RepoModel

sealed class AddingViewActon : BaseViewAction {

    object NavigateToResult : AddingViewActon()
    data class SearchClick(val key: String) : AddingViewActon()
    data class SaveClick(val repoModel: RepoModel) : AddingViewActon()

}