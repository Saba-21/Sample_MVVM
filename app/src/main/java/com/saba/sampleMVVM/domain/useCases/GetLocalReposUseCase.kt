package com.saba.sampleMVVM.domain.useCases

import com.saba.sampleMVVM.domain.models.apiModels.RepoModel
import com.saba.sampleMVVM.domain.repository.Repository
import com.saba.sampleMVVM.base.domain.BaseUseCase
import io.reactivex.Observable

class GetLocalReposUseCase(repository: Repository) :
    BaseUseCase<Unit, List<RepoModel>>(repository) {

    override fun createObservable(arg: Unit?):
            Observable<List<RepoModel>> = repository.getLocalRepos()

}