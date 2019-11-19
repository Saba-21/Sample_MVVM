package com.saba.sampleMVVM.base.domain

import androidx.lifecycle.LiveData
import com.saba.sampleMVVM.domain.repository.Repository

abstract class BaseUseCase<in A, B>
constructor(protected val repository: Repository) {

    abstract suspend fun createObservable(arg: A? = null): LiveData<B>

}