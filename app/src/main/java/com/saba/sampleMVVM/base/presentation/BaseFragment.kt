package com.saba.sampleMVVM.base.presentation

import android.arch.lifecycle.Observer
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.saba.sampleMVVM.base.presentation.eventHandling.WarningResponse
import com.saba.sampleMVVM.presentation.main.MainViewModel
import com.saba.sampleMVVM.presentation.main.MainViewState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.koin.android.ext.android.get
import org.koin.android.scope.ext.android.getScope
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

abstract class BaseFragment<ViewState : BaseViewState, ViewAction : BaseViewAction>(private val layoutId: Int) :
    Fragment() {

    private lateinit var baseViewModel: BaseViewModel<ViewState, ViewAction>
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var viewActionSubject: PublishSubject<ViewAction>
    private var navigationController: NavController? = null
    private val parentViewModel: MainViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationController = get(scope = getScope(ACTIVITY)) {
            parametersOf(activity!!)
        }

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
            baseViewModel.getErrorStateObservable().filter { isResumed }.subscribe { errorState ->
                postParentState(MainViewState.OnWarningReceived(errorState))
            }
        )

        baseViewModel.onSubscribeViewAction(viewActionSubject)

        onDraw(view, savedInstanceState)
    }

    protected fun postParentState(state: MainViewState) {
        parentViewModel.postState(state)
    }

    protected fun postAction(action: ViewAction) {
        if ((action.needsNetwork && checkNetwork() != false) || !action.needsNetwork)
            viewActionSubject.onNext(action)
        else
            postParentState(MainViewState.OnWarningReceived(WarningResponse.OFFLINE))
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

    protected fun onNavigate(navDirections: NavDirections) {
        navigationController?.navigate(navDirections)
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