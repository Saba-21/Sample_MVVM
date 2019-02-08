package com.saba.sample_mvvm.presentation.main

import android.os.Bundle
import com.saba.sample_mvvm.R
import com.saba.sample_mvvm.base.structure.BaseActivity
import com.saba.sample_mvvm.base.structure.LayoutResourceId
import com.saba.sample_mvvm.presentation.add.AddingFragment
import org.koin.android.viewmodel.ext.android.viewModel

@LayoutResourceId(R.layout.activity_main)
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun renderView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.frame_main, AddingFragment.newInstance()).commit()
        }
    }

}