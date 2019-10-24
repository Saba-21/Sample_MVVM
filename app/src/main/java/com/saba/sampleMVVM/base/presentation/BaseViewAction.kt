package com.saba.sampleMVVM.base.presentation

interface BaseViewAction {

    val needsNetwork: Boolean
    val needsLoader: Boolean

}