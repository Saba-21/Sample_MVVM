package com.saba.sample_mvvm.presentation.get

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import com.saba.sample_mvvm.R
import com.saba.sample_mvvm.custom.adapters.RepoAdapter
import com.saba.sample_mvvm.base.structure.BaseFragment
import com.saba.sample_mvvm.base.annotations.LayoutResourceId
import kotlinx.android.synthetic.main.fragment_result.*
import org.koin.android.viewmodel.ext.android.viewModel

@LayoutResourceId(R.layout.fragment_result)
class ResultFragment : BaseFragment<ResultViewState>() {

    private val viewModel: ResultViewModel by viewModel()
    private lateinit var adapter: RepoAdapter

    override fun onPassViewModel() = viewModel

    override fun onRender(viewState: ResultViewState) {
        when (viewState) {
            is ResultViewState.DrawRepoList -> {
                adapter.setData(viewState.repoList)
            }
            is ResultViewState.ShowLoading -> {
                loader.visibility = View.VISIBLE
            }
            is ResultViewState.HideLoading -> {
                loader.visibility = View.GONE
            }
            is ResultViewState.ShowItemDropped -> {
                Toast.makeText(context!!, "item dropped", Toast.LENGTH_SHORT).show()
            }
            is ResultViewState.Error -> {
                Toast.makeText(context!!, viewState.message, Toast.LENGTH_SHORT).show()
            }
            is ResultViewState.NavigateToAdding -> {
                activity?.onBackPressed()
            }
        }
    }

    override fun onDraw(view: View?, savedInstanceState: Bundle?) {
        adapter = RepoAdapter()
        rvLocalRepos.adapter = adapter
        rvLocalRepos.layoutManager = LinearLayoutManager(context)

        adapter.setClickListener {
            viewModel.onDeleteClicked(it)
        }
        butDrawAdding.setOnClickListener {
            viewModel.onNavigateToAdding()
        }
    }

}