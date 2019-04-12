package com.saba.sampleMVVM.base.structure

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel<ViewState : BaseViewState, ViewAction : BaseViewAction> : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
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

    fun onSubscribeViewAction(subject: Observable<ViewAction>) {
        compositeDisposable.add(
            subject
                .flatMap {
                    onActionReceived(it)
                }.subscribe({
                    postState(it)
                }, {

                })
        )
    }

    abstract fun onActionReceived(action: ViewAction): Observable<ViewState>

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

}