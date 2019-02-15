package com.saba.sample_mvvm.domain.dataProviders.local

import com.saba.sample_mvvm.domain.dataModels.apiModels.RepoModel
import com.saba.sample_mvvm.domain.dataModels.dbModels.UserWithRepos
import io.reactivex.Observable

interface LocalDataProvider{

    fun save(repoModel: RepoModel):Observable<RepoModel>

    fun drop(repoModel: RepoModel):Observable<RepoModel>

    fun select(): Observable<List<RepoModel>>

    fun selectOwnerWithRepos(): Observable<List<UserWithRepos>>

}
