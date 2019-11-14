package com.saba.sampleMVVM.base.presentation

import android.arch.lifecycle.Observer
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import com.saba.sampleMVVM.base.presentation.eventHandling.WarningResponse
import com.saba.sampleMVVM.presentation.main.MainViewState
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.viewmodel.ext.android.viewModelByClass
import kotlin.reflect.KClass

abstract class BaseActivity<ViewState : BaseViewState, ViewAction : BaseViewAction>(
    @LayoutRes private val layoutId: Int,
    viewModelClass: KClass<out BaseViewModel<ViewState, ViewAction>>
) :
    AppCompatActivity() {

    private lateinit var compositeDisposable: CompositeDisposable
    private var isViewResumed = false
    private val baseViewModel by viewModelByClass(viewModelClass)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        compositeDisposable = CompositeDisposable()

        baseViewModel.getStateAwareObservable().observe(this, Observer {
            it?.let { viewState ->
                onStateReceived(viewState)
            }
        })

        compositeDisposable.add(
            Observable.merge(
                baseViewModel.getStateFullObservable(),
                baseViewModel.getParentStateObservable().map { it as ViewState }
            ).filter {
                isViewResumed
            }.subscribe { viewState ->
                onStateReceived(viewState)
            }
        )

        onDraw(savedInstanceState)

    }

    protected fun postAction(action: ViewAction) {
        if ((action.needsNetwork && checkNetwork() != false) || !action.needsNetwork) {

            if (action.needsLoader)
                onStateReceived(MainViewState.ShowLoading as ViewState)

            baseViewModel.onSubscribeViewAction(action)

        } else {
            onStateReceived(MainViewState.OnWarningReceived(WarningResponse.OFFLINE) as ViewState)
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        baseViewModel.getStateAwareObservable().removeObservers(this)
        super.onDestroy()
    }

    protected abstract fun onDraw(savedInstanceState: Bundle?)

    protected abstract fun onStateReceived(viewState: ViewState)

    override fun onResume() {
        super.onResume()
        isViewResumed = true
    }

    override fun onPause() {
        isViewResumed = false
        super.onPause()
    }

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