package com.saba.sample_mvvm.presentation.get

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.View
import org.koin.android.viewmodel.ext.android.viewModel
import com.saba.sample_mvvm.R
import com.saba.sample_mvvm.base.structure.BaseFragment
import com.saba.sample_mvvm.base.structure.LayoutResourceId

@LayoutResourceId(R.layout.fragment_result)
class ResultFragment : BaseFragment() {

    private val viewModel: ResultViewModel by viewModel()

    override fun renderView(view: View?, savedInstanceState: Bundle?) {
        viewModel.onLocalReposReceived().observe(this, Observer {
            Log.e("", "")
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = ResultFragment()
    }

}