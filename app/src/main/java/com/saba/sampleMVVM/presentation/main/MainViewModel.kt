package com.saba.sampleMVVM.presentation.main

import com.saba.sampleMVVM.base.presentation.BaseViewModel
import io.reactivex.Observable

class MainViewModel : BaseViewModel<MainViewState, MainViewAction>() {

    override fun onActionReceived(action: MainViewAction): Observable<MainViewState> {
        return Observable.empty()
    }

}