package com.saba.sample_mvvm.presentation.get

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
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

        adapter.setClickListener {
            viewModel.onDeleteClicked(it)
        }
        butDrawAdding.setOnClickListener {
            activity?.onBackPressed()
        }

        viewModel.getViewStateObservable().observe(this, Observer {
            when (it) {
                is ResultViewState.DrawRepoList -> {
                    adapter.setData(it.repoList)
                }
                is ResultViewState.DrawDropItem -> {
                    Log.e("DrawDropItem", "...")
                }
                is ResultViewState.Loading -> {
                    Log.e("Loading", "...")
                }
                is ResultViewState.Error -> {
                    Log.e("Error", "...")
                }

            }
        })

    }

}