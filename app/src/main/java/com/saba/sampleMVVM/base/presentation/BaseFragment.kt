package com.saba.sampleMVVM.base.presentation

import androidx.lifecycle.Observer
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saba.sampleMVVM.base.presentation.eventHandling.WarningResponse
import com.saba.sampleMVVM.presentation.main.MainViewAction
import com.saba.sampleMVVM.presentation.main.MainViewModel
import com.saba.sampleMVVM.presentation.main.MainViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class BaseFragment<ViewState : BaseViewState, ViewAction : BaseViewAction>(
    @LayoutRes private val layoutId: Int,
    viewModelClass: KClass<out BaseViewModel<ViewState, ViewAction>>
) : androidx.fragment.app.Fragment() {

    private val parentViewModel: MainViewModel by sharedViewModel()
    private val baseViewModel by viewModel(viewModelClass)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseViewModel.getStateAwareObservable().observe(viewLifecycleOwner, Observer {
            it?.let { viewState ->
                onStateReceived(viewState)
            }
        })

        baseViewModel.getStateFullObservable().observe(viewLifecycleOwner, Observer {
            it?.let { viewState ->
                onStateReceived(viewState)
            }
        })

        baseViewModel.getParentStateObservable().observe(viewLifecycleOwner, Observer {
            it?.let { viewState ->
                postParentState(viewState)
            }
        })
        onDraw(view, savedInstanceState)
    }

    protected fun postParentState(state: MainViewState) {
        parentViewModel.postState(state)
    }

    protected fun postParentAction(action: MainViewAction) {
        if ((action.needsNetwork && checkNetwork() != false) || !action.needsNetwork) {

            if (action.needsLoader)
                postParentState(MainViewState.ShowLoading)

            parentViewModel.onSubscribeViewAction(action)

        } else {
            postParentState(MainViewState.OnWarningReceived(WarningResponse.OFFLINE))
        }
    }

    protected fun postAction(action: ViewAction) {
        if ((action.needsNetwork && checkNetwork() != false) || !action.needsNetwork) {

            if (action.needsLoader)
                postParentState(MainViewState.ShowLoading)

            baseViewModel.onSubscribeViewAction(action)

        } else {
            postParentState(MainViewState.OnWarningReceived(WarningResponse.OFFLINE))
        }
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