package com.saba.sampleMVVM.base.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.saba.sampleMVVM.base.presentation.eventHandling.handleEvent
import com.saba.sampleMVVM.presentation.main.MainViewState
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel<ViewState : BaseViewState, ViewAction : BaseViewAction> : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val stateFullSubject = BehaviorSubject.create<ViewState>()
    private val stateAwareSubject = MutableLiveData<ViewState>()
    private val parentStateSubject = BehaviorSubject.create<MainViewState>()
    private val actionSubject = PublishSubject.create<ViewAction>()

    fun getStateFullObservable() = stateFullSubject
    fun getStateAwareObservable() = stateAwareSubject
    fun getParentStateObservable() = parentStateSubject

    fun postState(viewState: ViewState) {
        if (viewState.isStateAware)
            stateAwareSubject.postValue(viewState)
        else
            stateFullSubject.onNext(viewState)
    }

    fun postAction(viewAction: ViewAction) {
        actionSubject.onNext(viewAction)
    }

    fun onSubscribeViewAction(subject: Observable<ViewAction>) {
        compositeDisposable.add(
            Observable.merge(
                subject,
                actionSubject
            ).flatMap { viewAction ->
                onActionReceived(viewAction)
                    .onErrorResumeNext { throwable: Throwable ->
                        throwable.handleEvent {
                            parentStateSubject.onNext(MainViewState.OnWarningReceived(it))
                        }
                        if (viewAction.needsLoader)
                            parentStateSubject.onNext(MainViewState.HideLoading)

                        Observable.empty()
                    }.map {

                        if (viewAction.needsLoader)
                            parentStateSubject.onNext(MainViewState.HideLoading)

                        it
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