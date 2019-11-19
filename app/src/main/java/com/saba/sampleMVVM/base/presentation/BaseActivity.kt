package com.saba.sampleMVVM.base.presentation

import androidx.lifecycle.Observer
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.saba.sampleMVVM.base.presentation.eventHandling.WarningResponse
import com.saba.sampleMVVM.presentation.main.MainViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class BaseActivity<ViewState : BaseViewState, ViewAction : BaseViewAction>(
    @LayoutRes private val layoutId: Int,
    viewModelClass: KClass<out BaseViewModel<ViewState, ViewAction>>
) :
    AppCompatActivity() {

    private var isViewResumed = false
    private val baseViewModel by viewModel(viewModelClass)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        baseViewModel.getStateAwareObservable().observe(this, Observer {
            it?.let { viewState ->
                onStateReceived(viewState)
            }
        })

        baseViewModel.getStateFullObservable().observe(this, Observer {
            it?.let { viewState ->
                onStateReceived(viewState)
            }
        })

        baseViewModel.getParentStateObservable().observe(this, Observer {
            it?.let { viewState ->
                onStateReceived(viewState as ViewState)
            }
        })

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