package com.saba.sampleMVVM.domain.repository

import androidx.lifecycle.LiveData
import com.saba.sampleMVVM.domain.providers.global.GlobalDataProvider
import com.saba.sampleMVVM.domain.models.RepoModel
import com.saba.sampleMVVM.domain.providers.local.RepoDatabase

class RepositoryImpl(
    private val globalDataProvider: GlobalDataProvider,
    private val database: RepoDatabase
) : Repository {

    override suspend fun getLocalRepos(): LiveData<List<RepoModel>> {
        return database.repoDao().select()
    }

    override suspend fun dropLocalRepos(repoModel: RepoModel): Boolean {
        database.repoDao().delete(repoModel)
        return true
    }

    override suspend fun saveLocalRepo(repoModel: RepoModel): Boolean {
        database.repoDao().save(repoModel)
        return true
    }

    override suspend fun getGlobalRepos(userName: String): List<RepoModel> {
        return globalDataProvider.getStarredRepos(userName)
    }

}