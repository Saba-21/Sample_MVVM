package com.saba.sample_mvvm.domain.useCases.base

import com.saba.sample_mvvm.domain.repository.Repository
import io.reactivex.Observable

abstract class BaseUseCase<in A, B>
constructor(protected val repository: Repository) {

    abstract fun createObservable(arg: A? = null): Observable<B>

}