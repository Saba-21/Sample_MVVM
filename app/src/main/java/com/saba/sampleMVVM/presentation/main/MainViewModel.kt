package com.saba.sampleMVVM.presentation.main

import com.saba.sampleMVVM.base.extensions.makeObservable
import com.saba.sampleMVVM.base.structure.BaseViewModel
import io.reactivex.Observable

class MainViewModel : BaseViewModel<MainViewState, MainViewAction>() {

    override fun onActionReceived(action: MainViewAction): Observable<MainViewState> {
        return MainViewState.INIT.makeObservable() //fake
    }

}