package com.saba.sampleMVVM.presentation.get

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.saba.sampleMVVM.R
import com.saba.sampleMVVM.base.structure.BaseFragment
import com.saba.sampleMVVM.custom.adapters.RepoAdapter
import kotlinx.android.synthetic.main.fragment_result.*
import org.koin.android.viewmodel.ext.android.viewModel

class ResultFragment : BaseFragment<ResultViewState, ResultViewAction>(R.layout.fragment_result) {

    private val viewModel: ResultViewModel by viewModel()
    private lateinit var adapter: RepoAdapter

    override fun onPassViewModel() = viewModel

    override fun onRender(viewState: ResultViewState) {
        when (viewState) {
            is ResultViewState.DrawRepoList -> {
                adapter.setData(viewState.repoList)
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

        postAction(ResultViewAction.LoadRepos)


        adapter.setClickListener {
            postAction(ResultViewAction.DropClicked(it))
        }
        butDrawAdding.setOnClickListener {
            postAction(ResultViewAction.NavigateToResult)
        }
    }

}