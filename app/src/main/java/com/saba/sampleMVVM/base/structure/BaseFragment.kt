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
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.get
import org.koin.android.scope.ext.android.getScope
import org.koin.core.parameter.parametersOf

abstract class BaseFragment<ViewState : BaseViewState> : Fragment() {

    private lateinit var baseViewModel: BaseViewModel<ViewState>
    private lateinit var compositeDisposable: CompositeDisposable
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

        onDraw(view, savedInstanceState)

        baseViewModel = onPassViewModel()

        compositeDisposable = CompositeDisposable()

        baseViewModel.getStateAwareObservable().observe(this, Observer {
            it?.let { viewState ->
                onRender(viewState)
            }
        })

        addStateFullSubject(
            baseViewModel.getStateFullObservable().filter { isResumed }.subscribe { viewState ->
                onRender(viewState)
            }
        )

    }

    override fun onDestroyView() {
        baseViewModel.getStateAwareObservable().removeObservers(this)
        clearStateFullSubjects()
        super.onDestroyView()
    }

    protected fun onNavigate(navDirections: NavDirections) {
        navigationController?.navigate(navDirections)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View? = null
        val layoutResourceId =
            javaClass.getAnnotation(com.saba.sampleMVVM.base.annotations.LayoutResourceId::class.java)
        if (layoutResourceId != null) {
            view = inflater.inflate(layoutResourceId.value, container, false)
        }
        return view
    }

    private fun addStateFullSubject(disposable: Disposable) {
        compositeDisposable.remove(disposable)
        compositeDisposable.add(disposable)
    }

    private fun clearStateFullSubjects() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }

    protected abstract fun onDraw(view: View?, savedInstanceState: Bundle?)

    protected abstract fun onPassViewModel(): BaseViewModel<ViewState>

    protected abstract fun onRender(viewState: ViewState)

}