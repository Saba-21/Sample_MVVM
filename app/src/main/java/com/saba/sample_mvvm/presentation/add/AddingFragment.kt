package com.saba.sample_mvvm.presentation.add

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.View
import com.saba.sample_mvvm.R
import com.saba.sample_mvvm.base.structure.BaseFragment
import com.saba.sample_mvvm.base.structure.LayoutResourceId
import kotlinx.android.synthetic.main.fragment_adding.*
import org.koin.android.viewmodel.ext.android.viewModel

@LayoutResourceId(R.layout.fragment_adding)
class AddingFragment : BaseFragment() {

    private val viewModel: AddingViewModel by viewModel()

    override fun renderView(view: View?, savedInstanceState: Bundle?) {
        viewModel.onSearchResultReceived().observe(this, Observer {
            Log.e("", "")
        })
        butSearch.setOnClickListener {
            viewModel.onSearchClicked(tvUsername.text.toString())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddingFragment()
    }

}