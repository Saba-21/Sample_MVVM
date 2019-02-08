package com.saba.sample_mvvm.domain.repository

import com.saba.sample_mvvm.base.extensions.async
import com.saba.sample_mvvm.domain.dataProviders.global.GlobalDataProvider
import com.saba.sample_mvvm.domain.dataProviders.local.LocalDataProvider
import com.saba.sample_mvvm.domain.dataModels.apiModels.RepoModel
import io.reactivex.Observable

class RepositoryImpl(
    private val globalDataProvider: GlobalDataProvider,
    private val localDataProvider: LocalDataProvider
) : Repository {

    override fun saveLocalRepo(repoModel: RepoModel):
            Observable<RepoModel> = localDataProvider.save(repoModel).async()

    override fun dropLocalRepos(repoModel: RepoModel):
            Observable<RepoModel> = localDataProvider.drop(repoModel).async()

    override fun getLocalRepos():
            Observable<List<RepoModel>> = localDataProvider.select().async()

    override fun getGlobalRepos(userName: String):
            Observable<List<RepoModel>> = globalDataProvider.getStarredRepos(userName).async()

}