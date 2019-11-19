package com.saba.sampleMVVM.domain.useCases

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.saba.sampleMVVM.domain.models.RepoModel
import com.saba.sampleMVVM.domain.repository.Repository
import com.saba.sampleMVVM.base.domain.BaseUseCase

class GetGlobalReposUseCase(repository: Repository) :
    BaseUseCase<String, List<RepoModel>>(repository) {

    override suspend fun createObservable(arg: String?):
            LiveData<List<RepoModel>> = liveData {
        emit(repository.getGlobalRepos(arg!!))
    }

}