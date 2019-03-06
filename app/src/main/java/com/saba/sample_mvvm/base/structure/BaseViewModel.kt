package com.saba.sample_mvvm.base.structure

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

open class BaseViewModel<ViewState : BaseViewState> : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun addDisposables(vararg disposable: Disposable) {
        disposable.forEach {
            compositeDisposable.remove(it)
            compositeDisposable.add(it)
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

    private val stateFullSubject = PublishSubject.create<ViewState>()

    private val stateAwareSubject = MutableLiveData<ViewState>()

    fun getStateFullObservable() = stateFullSubject

    fun getStateAwareObservable() = stateAwareSubject

    protected fun postState(viewState: ViewState) {
        if (viewState.isStateAware)
            stateAwareSubject.postValue(viewState)
        else
            stateFullSubject.onNext(viewState)
    }

}