package com.saba.sampleMVVM.base.presentation

import android.arch.lifecycle.Observer
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.saba.sampleMVVM.base.presentation.eventHandling.WarningResponse
import com.saba.sampleMVVM.presentation.main.MainViewState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

abstract class BaseActivity<ViewState : BaseViewState, ViewAction : BaseViewAction>(private val layoutId: Int) :
    AppCompatActivity() {

    private lateinit var baseViewModel: BaseViewModel<ViewState, ViewAction>
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var viewActionSubject: PublishSubject<ViewAction>
    private var isViewResumed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        baseViewModel = onPassViewModel()
        compositeDisposable = CompositeDisposable()
        viewActionSubject = PublishSubject.create()

        baseViewModel.getStateAwareObservable().observe(this, Observer {
            it?.let { viewState ->
                onStateReceived(viewState)
            }
        })

        compositeDisposable.add(
            baseViewModel.getStateFullObservable().filter { isViewResumed }.subscribe { viewState ->
                onStateReceived(viewState)
            }
        )

        compositeDisposable.add(
            baseViewModel.getErrorStateObservable().filter { isViewResumed }.subscribe { errorState ->
                onStateReceived(MainViewState.OnWarningReceived(errorState) as ViewState)
            }
        )

        baseViewModel.onSubscribeViewAction(viewActionSubject)

        onDraw(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        isViewResumed = true
    }

    override fun onPause() {
        isViewResumed = false
        super.onPause()
    }

    protected fun postAction(action: ViewAction) {
        if ((action.needsNetwork && checkNetwork() != false) || !action.needsNetwork)
            viewActionSubject.onNext(action)
        else
            onStateReceived(MainViewState.OnWarningReceived(WarningResponse.OFFLINE) as ViewState)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        baseViewModel.getStateAwareObservable().removeObservers(this)
        super.onDestroy()
    }

    protected abstract fun onDraw(savedInstanceState: Bundle?)

    protected abstract fun onPassViewModel(): BaseViewModel<ViewState, ViewAction>

    protected abstract fun onStateReceived(viewState: ViewState)

    protected fun checkNetwork(): Boolean? {
        return try {
            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        } catch (e: Exception) {
            null
        }
    }

}