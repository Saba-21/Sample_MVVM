package com.saba.sampleMVVM.presentation.add

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.saba.sampleMVVM.R
import com.saba.sampleMVVM.base.structure.BaseFragment
import com.saba.sampleMVVM.custom.adapters.RepoAdapter
import kotlinx.android.synthetic.main.fragment_adding.*
import org.koin.android.viewmodel.ext.android.viewModel

class AddingFragment : BaseFragment<AddingViewState, AddingViewActon>(R.layout.fragment_adding) {

    private val viewModel: AddingViewModel by viewModel()
    private lateinit var adapter: RepoAdapter

    override fun onPassViewModel() = viewModel

    override fun onStateReceived(viewState: AddingViewState) {
        when (viewState) {
            is AddingViewState.DrawRepoList -> {
                adapter.setData(viewState.repoList)
            }
            is AddingViewState.ShowItemAdded -> {
                Toast.makeText(context!!, "item saved", Toast.LENGTH_SHORT).show()
            }
            is AddingViewState.Error -> {
                Toast.makeText(context!!, viewState.message, Toast.LENGTH_SHORT).show()
            }
            is AddingViewState.ShowLoading -> {
                loader.visibility = View.VISIBLE
            }
            is AddingViewState.HideLoading -> {
                loader.visibility = View.GONE
            }
            is AddingViewState.NavigateToResult -> {
                onNavigate(AddingFragmentDirections.actionAddingFragmentToResultFragment())
            }
        }
    }

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

}