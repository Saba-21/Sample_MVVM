package com.saba.sample_mvvm.presentation.get

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import androidx.navigation.NavController
import com.saba.sample_mvvm.R
import com.saba.sample_mvvm.adapters.RepoAdapter
import com.saba.sample_mvvm.base.structure.BaseFragment
import com.saba.sample_mvvm.base.structure.LayoutResourceId
import kotlinx.android.synthetic.main.fragment_result.*
import org.koin.android.viewmodel.ext.android.viewModel

@LayoutResourceId(R.layout.fragment_result)
class ResultFragment : BaseFragment() {

    private val viewModel: ResultViewModel by viewModel()

    override fun renderView(view: View?, savedInstanceState: Bundle?, navigationController: NavController) {

        val adapter = RepoAdapter()
        rvLocalRepos.adapter = adapter
        rvLocalRepos.layoutManager = LinearLayoutManager(context)

        viewModel.onLocalReposReceived().observe(this, Observer {
            adapter.setData(it ?: emptyList())
        })
        adapter.setClickListener {
            viewModel.onDeleteClicked(it)
        }
        butDrawAdding.setOnClickListener {
            navigationController.navigate(ResultFragmentDirections.actionResultFragmentToAddingFragment())
        }
    }

}