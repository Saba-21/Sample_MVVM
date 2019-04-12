package com.saba.sampleMVVM.presentation.main

import android.os.Bundle
import com.saba.sampleMVVM.R
import com.saba.sampleMVVM.base.structure.ACTIVITY
import com.saba.sampleMVVM.base.structure.BaseActivity
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getOrCreateScope
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewState, MainViewAction>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModel()
    override fun onPassViewModel() = viewModel

    override fun onRender(viewState: MainViewState) {}

    override fun onDraw(savedInstanceState: Bundle?) {
        bindScope(getOrCreateScope(ACTIVITY))
    }

}