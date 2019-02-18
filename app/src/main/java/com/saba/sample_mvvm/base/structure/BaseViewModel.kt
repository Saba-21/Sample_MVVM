package com.saba.sample_mvvm.base.structure

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel<ViewState> : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun addDisposables(vararg disposable: Disposable) {
        disposable.forEach {
            compositeDisposable.remove(it)
            compositeDisposable.add(it)
        }
    }

    private val viewStateObservable = MutableLiveData<ViewState>()

    fun getViewStateObservable() = viewStateObservable

    protected fun postState(viewState: ViewState) {
        viewStateObservable.postValue(viewState)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}