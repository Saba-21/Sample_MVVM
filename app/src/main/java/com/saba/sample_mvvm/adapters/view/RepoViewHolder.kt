package com.saba.sample_mvvm.adapters.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saba.sample_mvvm.R
import com.saba.sample_mvvm.domain.dataModels.apiModels.RepoModel
import kotlinx.android.synthetic.main.repo_item.view.*

class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(
        repo: RepoModel?,
        itemClickListener: ((RepoModel) -> Unit)?
    ) {
        if (repo != null) {
            itemView.tvListUser.text = repo.owner.login
            itemView.tvListName.text = repo.name
            itemView.tvListLanguage.text = repo.language
            itemView.tvListStars.text = repo.starCount.toString()
            itemView.setOnClickListener {
                itemClickListener?.invoke(repo)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false)
            return RepoViewHolder(view)
        }
    }

}