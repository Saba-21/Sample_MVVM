package com.saba.sampleMVVM.domain.providers.global

import com.saba.sampleMVVM.domain.models.apiModels.RepoModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GlobalDataProvider {

    @GET("users/{user}/starred")
    fun getStarredRepos(@Path("user")userName: String): Single<List<RepoModel>>

}