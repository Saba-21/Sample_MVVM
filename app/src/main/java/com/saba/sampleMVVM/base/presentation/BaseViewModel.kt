package com.saba.sampleMVVM.base.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saba.sampleMVVM.base.presentation.eventHandling.handleEvent
import com.saba.sampleMVVM.custom.liveData.SingleLiveEvent
import com.saba.sampleMVVM.presentation.main.MainViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

abstract class BaseViewModel<ViewState : BaseViewState, ViewAction : BaseViewAction> : ViewModel() {

    private val stateAwareSubject = MutableLiveData<ViewState>()
    private val stateFullSubject = SingleLiveEvent<ViewState>()
    private val parentStateSubject = SingleLiveEvent<MainViewState>()

    fun getStateFullObservable() = stateFullSubject
    fun getStateAwareObservable() = stateAwareSubject
    fun getParentStateObservable() = parentStateSubject

    fun postState(viewState: ViewState) {
        if (viewState.isStateAware)
            stateAwareSubject.postValue(viewState)
        else
            stateFullSubject.postValue(viewState)
    }

    fun onSubscribeViewAction(viewAction: ViewAction) {
        viewModelScope.launch {
            try {
                var viewState: ViewState? = null
                withContext(Dispatchers.IO) {
                    viewState = onActionReceived(viewAction)
                }
                postState(viewState!!)
            } catch (e: Exception) {
                e.handleEvent {
                    parentStateSubject.postValue(MainViewState.OnWarningReceived(it))
                }
            } finally {
                if (viewAction.needsLoader)
                    parentStateSubject.postValue(MainViewState.HideLoading)
            }
        }
    }

    abstract suspend fun onActionReceived(action: ViewAction): ViewState

}