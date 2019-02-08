package com.saba.sample_mvvm.domain.dataProviders.global

import com.saba.sample_mvvm.domain.dataModels.apiModels.RepoModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GlobalDataProvider {

    @GET("users/{user}/starred")
    fun getStarredRepos(@Path("user")userName: String): Observable<List<RepoModel>>

}