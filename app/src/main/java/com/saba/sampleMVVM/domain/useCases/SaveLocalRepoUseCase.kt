package com.saba.sampleMVVM.domain.useCases

import com.saba.sampleMVVM.domain.models.apiModels.RepoModel
import com.saba.sampleMVVM.domain.repository.Repository
import com.saba.sampleMVVM.base.domain.BaseUseCase
import io.reactivex.Observable

class SaveLocalRepoUseCase(repository: Repository) :
    BaseUseCase<RepoModel, RepoModel>(repository) {

    override fun createObservable(arg: RepoModel?):
            Observable<RepoModel> = repository.saveLocalRepo(arg!!)

}