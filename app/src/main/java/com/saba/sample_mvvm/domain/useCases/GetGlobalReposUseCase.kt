package com.saba.sample_mvvm.domain.useCases

import com.saba.sample_mvvm.domain.dataModels.apiModels.RepoModel
import com.saba.sample_mvvm.domain.repository.Repository
import com.saba.sample_mvvm.domain.useCases.base.BaseUseCase
import io.reactivex.Observable

class GetGlobalReposUseCase(repository: Repository) :
    BaseUseCase<String, List<RepoModel>>(repository) {

    override fun createObservable(arg: String?):
            Observable<List<RepoModel>> = repository.getGlobalRepos(arg!!)

}