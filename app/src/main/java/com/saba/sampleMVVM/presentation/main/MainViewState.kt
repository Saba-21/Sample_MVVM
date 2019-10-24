package com.saba.sampleMVVM.presentation.main

import com.saba.sampleMVVM.base.presentation.BaseViewState
import com.saba.sampleMVVM.base.presentation.eventHandling.WarningResponse

sealed class MainViewState(override val isStateAware: Boolean = false) : BaseViewState {

    data class OnWarningReceived(val warning: WarningResponse) : MainViewState()
    object ShowItemAdded : MainViewState()
    object ShowItemDropped : MainViewState()
    object NavigateToAdding : MainViewState()
    object NavigateToResult : MainViewState()
    object ShowLoading : MainViewState()
    object HideLoading : MainViewState()

}