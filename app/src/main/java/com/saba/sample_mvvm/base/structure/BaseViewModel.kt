package com.saba.sample_mvvm.base.structure

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

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

    private val stateFullObservable = MutableLiveData<ViewState>()

    private val stateAwareObservable = MutableLiveData<ViewState>()

    fun getStateFullObservable() = stateFullObservable

    fun getStateAwareObservable() = stateAwareObservable

    protected fun postState(viewState: ViewState) {
        if (viewState.isStateAware)
            stateAwareObservable.postValue(viewState)
        else
            stateFullObservable.postValue(viewState)
    }

}