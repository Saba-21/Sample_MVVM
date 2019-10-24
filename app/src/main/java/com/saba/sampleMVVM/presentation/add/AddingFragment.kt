package com.saba.sampleMVVM.presentation.add

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.saba.sampleMVVM.R
import com.saba.sampleMVVM.base.presentation.BaseFragment
import com.saba.sampleMVVM.custom.adapters.RepoAdapter
import kotlinx.android.synthetic.main.fragment_adding.*
import org.koin.android.viewmodel.ext.android.viewModel

class AddingFragment : BaseFragment<AddingViewState, AddingViewActon>(R.layout.fragment_adding) {

    private val viewModel: AddingViewModel by viewModel()
    override fun onPassViewModel() = viewModel

    private lateinit var adapter: RepoAdapter

    override fun onDraw(view: View?, savedInstanceState: Bundle?) {
        adapter = RepoAdapter()
        rvGlobalRepos.adapter = adapter
        rvGlobalRepos.layoutManager = LinearLayoutManager(context)

        adapter.setClickListener {
            postAction(AddingViewActon.SaveClick(it))
        }
        butSearch.setOnClickListener {
            postAction(AddingViewActon.SearchClick(tvUsername.text.toString()))
        }
        butDrawResult.setOnClickListener {
            postAction(AddingViewActon.NavigateToResult)
        }
    }

    override fun onStateReceived(viewState: AddingViewState) {
        when (viewState) {
            is AddingViewState.DrawRepoList -> {
                adapter.setData(viewState.repoList)
            }
            is AddingViewState.ShowItemAdded -> {
                Toast.makeText(context!!, "item saved", Toast.LENGTH_SHORT).show()
            }
            is AddingViewState.NavigateToResult -> {
                onNavigate(AddingFragmentDirections.actionAddingFragmentToResultFragment())
            }
        }
    }

}