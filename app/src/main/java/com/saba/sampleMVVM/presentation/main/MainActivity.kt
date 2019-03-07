package com.saba.sampleMVVM.presentation.main

import android.os.Bundle
import com.saba.sampleMVVM.R
import com.saba.sampleMVVM.base.structure.BaseActivity
import com.saba.sampleMVVM.base.structure.ACTIVITY
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getOrCreateScope
import org.koin.android.viewmodel.ext.android.viewModel

@com.saba.sampleMVVM.base.annotations.LayoutResourceId(R.layout.activity_main)
class MainActivity : BaseActivity<MainViewState>() {

    private val viewModel: MainViewModel by viewModel()

    override fun onPassViewModel() = viewModel

    override fun onRender(viewState: MainViewState) {

    }

    override fun onDraw(savedInstanceState: Bundle?) {
        bindScope(getOrCreateScope(ACTIVITY))
    }

}