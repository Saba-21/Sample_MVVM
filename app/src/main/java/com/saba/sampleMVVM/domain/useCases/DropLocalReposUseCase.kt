package com.saba.sampleMVVM.domain.useCases

import com.saba.sampleMVVM.domain.models.RepoModel
import com.saba.sampleMVVM.domain.repository.Repository
import com.saba.sampleMVVM.base.domain.BaseUseCase

class DropLocalReposUseCase(repository: Repository) :
    BaseUseCase<RepoModel, Boolean>(repository) {

    override suspend fun createObservable(arg: RepoModel?): Boolean =
        repository.dropLocalRepos(arg!!)

}