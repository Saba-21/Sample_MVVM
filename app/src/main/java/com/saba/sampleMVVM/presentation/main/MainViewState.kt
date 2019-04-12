package com.saba.sampleMVVM.presentation.main

import com.saba.sampleMVVM.base.structure.BaseViewState

sealed class MainViewState(override val isStateAware: Boolean = false) : BaseViewState{

    object INIT: MainViewState()

}