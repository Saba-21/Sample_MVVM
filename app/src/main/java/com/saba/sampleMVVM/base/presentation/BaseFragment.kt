package com.saba.sampleMVVM.base.presentation

import android.arch.lifecycle.Observer
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saba.sampleMVVM.base.presentation.eventHandling.WarningResponse
import com.saba.sampleMVVM.presentation.main.MainViewAction
import com.saba.sampleMVVM.presentation.main.MainViewModel
import com.saba.sampleMVVM.presentation.main.MainViewState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.koin.android.viewmodel.ext.android.sharedViewModel

abstract class BaseFragment<ViewState : BaseViewState, ViewAction : BaseViewAction>(private val layoutId: Int) :
    Fragment() {

    private lateinit var baseViewModel: BaseViewModel<ViewState, ViewAction>
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var viewActionSubject: PublishSubject<ViewAction>
    private val parentViewModel: MainViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseViewModel = onPassViewModel()
        compositeDisposable = CompositeDisposable()
        viewActionSubject = PublishSubject.create()

        baseViewModel.getStateAwareObservable().observe(this, Observer {
            it?.let { viewState ->
                onStateReceived(viewState)
            }
        })

        compositeDisposable.add(
            baseViewModel.getStateFullObservable().filter { isResumed }.subscribe { viewState ->
                onStateReceived(viewState)
            }
        )

        compositeDisposable.add(
            baseViewModel.getParentStateObservable().filter { isResumed }.subscribe { viewState ->
                postParentState(viewState)
            }
        )

        baseViewModel.onSubscribeViewAction(viewActionSubject)

        onDraw(view, savedInstanceState)
    }

    protected fun postParentState(state: MainViewState) {
        parentViewModel.postState(state)
    }

    protected fun postParentAction(action: MainViewAction) {
        if ((action.needsNetwork && checkNetwork() != false) || !action.needsNetwork) {
            parentViewModel.postAction(action)

            if (action.needsLoader)
                postParentState(MainViewState.ShowLoading)
        } else {
            postParentState(MainViewState.OnWarningReceived(WarningResponse.OFFLINE))
        }
    }

    protected fun postAction(action: ViewAction) {
        if ((action.needsNetwork && checkNetwork() != false) || !action.needsNetwork) {
            viewActionSubject.onNext(action)

            if (action.needsLoader)
                postParentState(MainViewState.ShowLoading)
        } else {
            postParentState(MainViewState.OnWarningReceived(WarningResponse.OFFLINE))
        }
    }

    override fun onDestroyView() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        baseViewModel.getStateAwareObservable().removeObservers(this)
        super.onDestroyView()
    }

    override fun onDestroy() {
        baseViewModel.getStateAwareObservable().removeObservers(this)
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    protected abstract fun onDraw(view: View?, savedInstanceState: Bundle?)

    protected abstract fun onPassViewModel(): BaseViewModel<ViewState, ViewAction>

    protected abstract fun onStateReceived(viewState: ViewState)

    protected fun checkNetwork(): Boolean? {
        return try {
            val connectivityManager =
                activity!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        } catch (e: Exception) {
            null
        }
    }

}