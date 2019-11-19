package com.saba.sampleMVVM.domain.useCases

import com.saba.sampleMVVM.domain.models.RepoModel
import com.saba.sampleMVVM.domain.repository.Repository
import com.saba.sampleMVVM.base.domain.BaseUseCase

class GetGlobalReposUseCase(repository: Repository) :
    BaseUseCase<String, List<RepoModel>>(repository) {

    override suspend fun createObservable(arg: String?):
            List<RepoModel> = repository.getGlobalRepos(arg!!)

}