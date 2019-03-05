package com.saba.sample_mvvm.presentation.add

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import com.saba.sample_mvvm.R
import com.saba.sample_mvvm.adapters.RepoAdapter
import com.saba.sample_mvvm.base.structure.BaseFragment
import com.saba.sample_mvvm.base.annotations.LayoutResourceId
import kotlinx.android.synthetic.main.fragment_adding.*
import org.koin.android.viewmodel.ext.android.viewModel

@LayoutResourceId(R.layout.fragment_adding)
class AddingFragment : BaseFragment<AddingViewState>() {

    private val viewModel: AddingViewModel by viewModel()
    private lateinit var adapter: RepoAdapter

    override fun onPassViewModel() = viewModel

    override fun onRender(viewState: AddingViewState) {
        when (viewState) {
            is AddingViewState.DrawRepoList -> {
                adapter.setData(viewState.repoList)
            }
            is AddingViewState.Loading -> {
                Log.e("Loading", "...")
            }
            is AddingViewState.Error -> {
                Log.e("Error", "...")
            }
        }
    }

    override fun onDraw(view: View?, savedInstanceState: Bundle?, navigationController: NavController) {
        adapter = RepoAdapter()
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

    }

}