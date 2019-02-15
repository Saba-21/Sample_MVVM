package com.saba.sample_mvvm.domain.useCases

import com.saba.sample_mvvm.domain.dataModels.dbModels.UserWithRepos
import com.saba.sample_mvvm.domain.repository.Repository
import com.saba.sample_mvvm.domain.useCases.base.BaseUseCase
import io.reactivex.Observable

class SelectUserWithReposUseCase(repository: Repository) :
    BaseUseCase<Unit, List<UserWithRepos>>(repository) {

    override fun createObservable(arg: Unit?):
            Observable<List<UserWithRepos>> = repository.selectOwnerWithRepos()

}