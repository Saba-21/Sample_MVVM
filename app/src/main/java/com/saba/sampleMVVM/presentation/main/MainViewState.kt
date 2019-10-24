package com.saba.sampleMVVM.presentation.main

import com.saba.sampleMVVM.base.presentation.BaseViewState

sealed class MainViewState(
    override val isStateAware: Boolean = false
) : BaseViewState