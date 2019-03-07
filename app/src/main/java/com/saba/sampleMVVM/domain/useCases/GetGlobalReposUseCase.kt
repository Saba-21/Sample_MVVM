package com.saba.sampleMVVM.domain.useCases

import com.saba.sampleMVVM.domain.models.apiModels.RepoModel
import com.saba.sampleMVVM.domain.repository.Repository
import com.saba.sampleMVVM.base.useCase.BaseUseCase
import io.reactivex.Observable

class GetGlobalReposUseCase(repository: Repository) :
    BaseUseCase<String, List<RepoModel>>(repository) {

    override fun createObservable(arg: String?):
            Observable<List<RepoModel>> = repository.getGlobalRepos(arg!!)

}