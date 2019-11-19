package com.saba.sampleMVVM.domain.repository

import androidx.lifecycle.LiveData
import com.saba.sampleMVVM.domain.models.RepoModel

interface Repository {

    suspend fun getGlobalRepos(userName: String): List<RepoModel>

    suspend fun getLocalRepos(): LiveData<List<RepoModel>>

    suspend fun dropLocalRepos(repoModel: RepoModel): Boolean

    suspend fun saveLocalRepo(repoModel: RepoModel): Boolean

}
