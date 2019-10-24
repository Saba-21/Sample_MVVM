package com.saba.sampleMVVM.base.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.saba.sampleMVVM.base.presentation.eventHandling.WarningResponse
import com.saba.sampleMVVM.base.presentation.eventHandling.handleEvent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseViewModel<ViewState : BaseViewState, ViewAction : BaseViewAction> : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val stateFullSubject = BehaviorSubject.create<ViewState>()
    private val errorStateSubject = BehaviorSubject.create<WarningResponse>()
    private val stateAwareSubject = MutableLiveData<ViewState>()

    fun getStateFullObservable() = stateFullSubject
    fun getStateAwareObservable() = stateAwareSubject
    fun getErrorStateObservable() = errorStateSubject

    fun postState(viewState: ViewState) {
        if (viewState.isStateAware)
            stateAwareSubject.postValue(viewState)
        else
            stateFullSubject.onNext(viewState)
    }

    fun onSubscribeViewAction(subject: Observable<ViewAction>) {
        compositeDisposable.add(
            subject.flatMap { viewAction ->
                onActionReceived(viewAction)
                    .onErrorResumeNext { throwable: Throwable ->
                        throwable.handleEvent {
                            errorStateSubject.onNext(it)
                        }
                        Observable.empty()
                    }
            }.subscribe({ viewState ->
                postState(viewState)
            }, {

            })
        )
    }

    abstract fun onActionReceived(action: ViewAction): Observable<ViewState>

    protected fun addSubscriptions(vararg disposable: Disposable) {
        compositeDisposable.addAll(*disposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

}