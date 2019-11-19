package com.saba.sampleMVVM.domain.useCases

import androidx.lifecycle.LiveData
import com.saba.sampleMVVM.domain.models.RepoModel
import com.saba.sampleMVVM.domain.repository.Repository
import com.saba.sampleMVVM.base.domain.BaseUseCase

class GetLocalReposUseCase(repository: Repository) :
    BaseUseCase<Unit, LiveData<List<RepoModel>>>(repository) {

    override suspend fun createObservable(arg: Unit?):
            LiveData<List<RepoModel>> = repository.getLocalRepos()

}