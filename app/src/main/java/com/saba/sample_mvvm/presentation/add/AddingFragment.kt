package com.saba.sample_mvvm.presentation.add

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
import kotlinx.android.synthetic.main.fragment_adding.*
import org.koin.android.viewmodel.ext.android.viewModel

@LayoutResourceId(R.layout.fragment_adding)
class AddingFragment : BaseFragment() {

    private val viewModel: AddingViewModel by viewModel()

    override fun onDraw(view: View?, savedInstanceState: Bundle?, navigationController: NavController) {

        val adapter = RepoAdapter()
        rvGlobalRepos.adapter = adapter
        rvGlobalRepos.layoutManager = LinearLayoutManager(context)

        adapter.setClickListener {
            viewModel.onSaveClicked(it)
        }
        butSearch.setOnClickListener {
            viewModel.onSearchClicked(tvUsername.text.toString())
        }
        butDrawResult.setOnClickListener {
            navigationController.navigate(AddingFragmentDirections.actionAddingFragmentToResultFragment())
        }

        viewModel.getViewStateObservable().observe(this, Observer {
            when (it) {
                is AddingViewState.DrawRepoList -> {
                    adapter.setData(it.repoList)
                }
                is AddingViewState.DrawSaveItem -> {
                    Log.e("DrawSaveItem", "...")
                }
                is AddingViewState.Loading -> {
                    Log.e("Loading", "...")
                }
                is AddingViewState.Error -> {
                    Log.e("Error", "...")
                }

            }
        })
    }

}