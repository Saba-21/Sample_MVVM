package com.saba.sampleMVVM.domain.providers.global

import com.saba.sampleMVVM.domain.models.RepoModel
import retrofit2.http.GET
import retrofit2.http.Path

interface GlobalDataProvider {

    @GET("users/{user}/starred")
    suspend fun getStarredRepos(@Path("user") userName: String): List<RepoModel>

}