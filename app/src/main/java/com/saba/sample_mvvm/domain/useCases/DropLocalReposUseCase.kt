package com.saba.sample_mvvm.domain.useCases

import com.saba.sample_mvvm.domain.dataModels.apiModels.RepoModel
import com.saba.sample_mvvm.domain.repository.Repository
import com.saba.sample_mvvm.domain.useCases.base.BaseUseCase
import io.reactivex.Observable

class DropLocalReposUseCase(repository: Repository) :
    BaseUseCase<RepoModel, RepoModel>(repository) {

    override fun createObservable(arg: RepoModel?):
            Observable<RepoModel> = repository.dropLocalRepos(arg!!)

}