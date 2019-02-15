package com.saba.sample_mvvm.presentation.main

import android.os.Bundle
import com.saba.sample_mvvm.R
import com.saba.sample_mvvm.base.structure.BaseActivity
import com.saba.sample_mvvm.base.structure.LayoutResourceId
import com.saba.sample_mvvm.custom.ACTIVITY
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getOrCreateScope

@LayoutResourceId(R.layout.activity_main)
class MainActivity : BaseActivity() {

    override fun renderView(savedInstanceState: Bundle?) {
        bindScope(getOrCreateScope(ACTIVITY))
    }

}