package com.saba.sample_mvvm.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.saba.sample_mvvm.adapters.view.RepoViewHolder
import com.saba.sample_mvvm.domain.dataModels.apiModels.RepoModel

class RepoAdapter : RecyclerView.Adapter<RepoViewHolder>() {

    private var repoList = emptyList<RepoModel>()

    private var itemClickListener: ((RepoModel) -> Unit)? = null

    fun setClickListener(listener: (RepoModel) -> Unit) {
        itemClickListener = listener
    }

    fun setData(repoList: List<RepoModel>) {
        this.repoList = repoList
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): RepoViewHolder {
        return RepoViewHolder.create(viewGroup)
    }

    override fun onBindViewHolder(viewHolder: RepoViewHolder, position: Int) {
        viewHolder.bind(repoList[position], itemClickListener)
    }

    override fun getItemCount(): Int = repoList.size

}