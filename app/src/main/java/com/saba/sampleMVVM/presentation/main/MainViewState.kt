package com.saba.sampleMVVM.presentation.main

import com.saba.sampleMVVM.base.presentation.BaseViewState
import com.saba.sampleMVVM.base.presentation.eventHandling.WarningResponse

sealed class MainViewState(override val isStateAware: Boolean = false) : BaseViewState {

    data class OnWarningReceived(val warning: WarningResponse) : MainViewState()

}