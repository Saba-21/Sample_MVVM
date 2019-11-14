package com.saba.sampleMVVM.presentation.get

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.saba.sampleMVVM.R
import com.saba.sampleMVVM.base.presentation.BaseFragment
import com.saba.sampleMVVM.base.presentation.eventHandling.logName
import com.saba.sampleMVVM.custom.adapters.RepoAdapter
import com.saba.sampleMVVM.presentation.main.MainViewAction
import com.saba.sampleMVVM.presentation.main.MainViewState
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment : BaseFragment<ResultViewState, ResultViewAction>(
    R.layout.fragment_result,
    ResultViewModel::class
) {

    private lateinit var adapter: RepoAdapter

    override fun onDraw(view: View?, savedInstanceState: Bundle?) {
        adapter = RepoAdapter()
        rvLocalRepos.adapter = adapter
        rvLocalRepos.layoutManager = LinearLayoutManager(context)

        postAction(ResultViewAction.LoadRepos)

        adapter.setClickListener {
            postParentAction(MainViewAction.DropClicked(it))
        }

        butDrawAdding.setOnClickListener {
            postParentState(MainViewState.NavigateToAdding)
        }
    }

    override fun onStateReceived(viewState: ResultViewState) {
        viewState.logName()
        when (viewState) {
            is ResultViewState.DrawRepoList -> {
                adapter.setData(viewState.repoList)
            }
        }
    }

}