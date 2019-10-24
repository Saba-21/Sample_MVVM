package com.saba.sampleMVVM.domain.providers.local

import com.saba.sampleMVVM.domain.models.apiModels.RepoModel
import io.reactivex.Observable

interface LocalDataProvider{

    fun save(repoModel: RepoModel):Observable<RepoModel>

    fun drop(repoModel: RepoModel):Observable<RepoModel>

    fun select(): Observable<List<RepoModel>>

}
