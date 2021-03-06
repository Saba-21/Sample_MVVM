package com.saba.sampleMVVM.domain.repository

import com.saba.sampleMVVM.custom.extensions.async
import com.saba.sampleMVVM.domain.providers.global.GlobalDataProvider
import com.saba.sampleMVVM.domain.providers.local.LocalDataProvider
import com.saba.sampleMVVM.domain.models.apiModels.RepoModel
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
            Observable<List<RepoModel>> = globalDataProvider.getStarredRepos(userName).toObservable().async()

}