package com.saba.sampleMVVM.domain.useCases

import com.saba.sampleMVVM.domain.models.dbModels.UserWithRepos
import com.saba.sampleMVVM.domain.repository.Repository
import com.saba.sampleMVVM.base.useCase.BaseUseCase
import io.reactivex.Observable

class SelectUserWithReposUseCase(repository: Repository) :
    BaseUseCase<Unit, List<UserWithRepos>>(repository) {

    override fun createObservable(arg: Unit?):
            Observable<List<UserWithRepos>> = repository.selectOwnerWithRepos()

}