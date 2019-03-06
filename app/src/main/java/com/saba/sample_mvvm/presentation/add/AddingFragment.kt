package com.saba.sample_mvvm.presentation.add

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.saba.sample_mvvm.R
import com.saba.sample_mvvm.custom.adapters.RepoAdapter
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
            is AddingViewState.ShowLoading -> {
                loader.visibility = View.VISIBLE
            }
            is AddingViewState.HideLoading -> {
                loader.visibility = View.GONE
            }
            is AddingViewState.ShowItemAdded -> {
                Toast.makeText(context!!, "item saved", Toast.LENGTH_SHORT).show()
            }
            is AddingViewState.Error -> {
                Toast.makeText(context!!, viewState.message, Toast.LENGTH_SHORT).show()
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
            viewModel.onSaveClicked(it)
        }
        butSearch.setOnClickListener {
            viewModel.onSearchClicked(tvUsername.text.toString())
        }
        butDrawResult.setOnClickListener {
            viewModel.onNavigateToResult()
        }
    }

}