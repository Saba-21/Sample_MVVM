package com.saba.sample_mvvm.presentation.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.saba.sample_mvvm.R
import com.saba.sample_mvvm.base.structure.BaseActivity
import com.saba.sample_mvvm.base.structure.LayoutResourceId
import com.saba.sample_mvvm.custom.ACTIVITY
import org.koin.android.ext.android.inject
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getOrCreateScope
import org.koin.android.viewmodel.ext.android.viewModel

@LayoutResourceId(R.layout.activity_main)
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun renderView(savedInstanceState: Bundle?) {
        bindScope(getOrCreateScope(ACTIVITY))
    }

}