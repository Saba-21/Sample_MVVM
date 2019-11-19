package com.saba.sampleMVVM.domain.useCases

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.saba.sampleMVVM.domain.models.RepoModel
import com.saba.sampleMVVM.domain.repository.Repository
import com.saba.sampleMVVM.base.domain.BaseUseCase

class SaveLocalRepoUseCase(repository: Repository) :
    BaseUseCase<RepoModel, Boolean>(repository) {

    override suspend fun createObservable(arg: RepoModel?):
            LiveData<Boolean> = liveData {
        emit(repository.saveLocalRepo(arg!!))
    }

}