package com.saba.sample_mvvm.custom.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saba.sample_mvvm.R
import com.saba.sample_mvvm.domain.dataModels.apiModels.RepoModel
import kotlinx.android.synthetic.main.repo_item.view.*

class RepoAdapter : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    private var repoList = emptyList<RepoModel>()

    private var itemClickListener: ((RepoModel) -> Unit)? = null

    fun setClickListener(listener: (RepoModel) -> Unit) {
        itemClickListener = listener
    }

    fun setData(repoList: List<RepoModel>) {
        this.repoList = repoList.sortedBy {
            -it.starCount
        }
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): RepoViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.repo_item, viewGroup, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RepoViewHolder, position: Int) {
        viewHolder.bind(repoList[position], itemClickListener)
    }

    override fun getItemCount(): Int = repoList.size

    inner class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(repo: RepoModel, itemClickListener: ((RepoModel) -> Unit)?) {
            itemView.tvListUser.text = repo.owner.login
            itemView.tvListName.text = repo.name
            itemView.tvListLanguage.text = repo.language
            itemView.tvListStars.text = repo.starCount.toString()
            itemView.ivListAvatar.setImageURI(repo.owner.avatarUrl)
            itemView.setOnClickListener {
                itemClickListener?.invoke(repo)
            }
        }

    }

}