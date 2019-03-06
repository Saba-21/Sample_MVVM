package com.saba.sample_mvvm.presentation.main

import android.os.Bundle
import com.saba.sample_mvvm.R
import com.saba.sample_mvvm.base.structure.BaseActivity
import com.saba.sample_mvvm.base.annotations.LayoutResourceId
import com.saba.sample_mvvm.base.structure.ACTIVITY
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getOrCreateScope
import org.koin.android.viewmodel.ext.android.viewModel

@LayoutResourceId(R.layout.activity_main)
class MainActivity : BaseActivity<MainViewState>() {

    private val viewModel: MainViewModel by viewModel()

    override fun onPassViewModel() = viewModel

    override fun onRender(viewState: MainViewState) {

    }

    override fun onDraw(savedInstanceState: Bundle?) {
        bindScope(getOrCreateScope(ACTIVITY))
    }

}