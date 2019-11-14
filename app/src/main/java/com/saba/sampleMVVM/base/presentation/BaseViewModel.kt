package com.saba.sampleMVVM.base.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.saba.sampleMVVM.base.presentation.eventHandling.handleEvent
import com.saba.sampleMVVM.presentation.main.MainViewState
import io.reactivex.subjects.BehaviorSubject
import java.lang.Exception

abstract class BaseViewModel<ViewState : BaseViewState, ViewAction : BaseViewAction> : ViewModel() {

    private val stateFullSubject = BehaviorSubject.create<ViewState>()
    private val stateAwareSubject = MutableLiveData<ViewState>()
    private val parentStateSubject = BehaviorSubject.create<MainViewState>()

    fun getStateFullObservable() = stateFullSubject
    fun getStateAwareObservable() = stateAwareSubject
    fun getParentStateObservable() = parentStateSubject

    fun postState(viewState: ViewState) {
        if (viewState.isStateAware)
            stateAwareSubject.postValue(viewState)
        else
            stateFullSubject.onNext(viewState)
    }

    fun onSubscribeViewAction(viewAction: ViewAction) {
        try {
            val state = onActionReceived(viewAction)
            postState(state)
        } catch (e: Exception) {
            e.handleEvent {
                parentStateSubject.onNext(MainViewState.OnWarningReceived(it))
            }
        } finally {
            if (viewAction.needsLoader)
                parentStateSubject.onNext(MainViewState.HideLoading)
        }
    }

    abstract fun onActionReceived(action: ViewAction): ViewState

}