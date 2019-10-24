package com.saba.sampleMVVM.presentation.main

import com.saba.sampleMVVM.base.presentation.BaseViewAction

sealed class MainViewAction(
    override val needsNetwork: Boolean = false,
    override val needsLoader: Boolean = false
) : BaseViewAction