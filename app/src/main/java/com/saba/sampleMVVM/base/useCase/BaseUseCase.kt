package com.saba.sampleMVVM.base.useCase

import com.saba.sampleMVVM.domain.repository.Repository
import io.reactivex.Observable

abstract class BaseUseCase<in A, B>
constructor(protected val repository: Repository) {

    abstract fun createObservable(arg: A? = null): Observable<B>

}