package com.saba.sampleMVVM.base.structure

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.koin.android.ext.android.get
import org.koin.android.scope.ext.android.getScope
import org.koin.core.parameter.parametersOf

abstract class BaseFragment<ViewState : BaseViewState, ViewAction : BaseViewAction>(private val layoutId: Int) :
    Fragment() {

    private lateinit var baseViewModel: BaseViewModel<ViewState, ViewAction>
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var viewActionSubject: PublishSubject<ViewAction>
    private var navigationController: NavController? = null

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

        baseViewModel.onSubscribeViewAction(viewActionSubject)

        onDraw(view, savedInstanceState)

        baseViewModel.getStateAwareObservable().observe(this, Observer {
            it?.let { viewState ->
                onRender(viewState)
            }
        })

        compositeDisposable.add(
            baseViewModel.getStateFullObservable().filter { isResumed }.subscribe { viewState ->
                onRender(viewState)
            }
        )
    }

    protected fun postAction(action: ViewAction) {
        viewActionSubject.onNext(action)
    }

    override fun onDestroyView() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onDestroyView()
    }

    override fun onDestroy() {
        baseViewModel.getStateAwareObservable().removeObservers(this)
        super.onDestroy()
    }

    protected fun onNavigate(navDirections: NavDirections) {
        navigationController?.navigate(navDirections)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    protected abstract fun onDraw(view: View?, savedInstanceState: Bundle?)

    protected abstract fun onPassViewModel(): BaseViewModel<ViewState, ViewAction>

    protected abstract fun onRender(viewState: ViewState)

}