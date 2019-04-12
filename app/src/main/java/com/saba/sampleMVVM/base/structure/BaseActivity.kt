package com.saba.sampleMVVM.base.structure

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

        onDraw(savedInstanceState)

        baseViewModel.onSubscribeViewAction(viewActionSubject)

        baseViewModel.getStateAwareObservable().observe(this, Observer {
            it?.let { viewState ->
                onRender(viewState)
            }
        })

        compositeDisposable.add(
            baseViewModel.getStateFullObservable().filter { isViewResumed }.subscribe { viewState ->
                onRender(viewState)
            }
        )
    }

    protected fun postAction(action: ViewAction) {
        viewActionSubject.onNext(action)
    }

    override fun onResume() {
        super.onResume()
        isViewResumed = true
    }

    override fun onPause() {
        isViewResumed = false
        super.onPause()
    }

    override fun onDestroy() {
        baseViewModel.getStateAwareObservable().removeObservers(this)
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onDestroy()
    }

    protected abstract fun onDraw(savedInstanceState: Bundle?)

    protected abstract fun onPassViewModel(): BaseViewModel<ViewState, ViewAction>

    protected abstract fun onRender(viewState: ViewState)

}