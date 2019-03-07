package com.saba.sampleMVVM.base.structure

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.saba.sampleMVVM.base.annotations.LayoutResourceId
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity<ViewState : BaseViewState> : AppCompatActivity() {

    private lateinit var baseViewModel: BaseViewModel<ViewState>
    private lateinit var compositeDisposable: CompositeDisposable
    private var isViewResumed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutResourceId = javaClass.getAnnotation(LayoutResourceId::class.java)
        if (layoutResourceId != null)
            setContentView(layoutResourceId.value)

        onDraw(savedInstanceState)

        baseViewModel = onPassViewModel()

        compositeDisposable = CompositeDisposable()

        baseViewModel.getStateAwareObservable().observe(this, Observer {
            it?.let { viewState ->
                onRender(viewState)
            }
        })

        addStateFullSubject(
            baseViewModel.getStateFullObservable().filter { isViewResumed }.subscribe { viewState ->
                onRender(viewState)
            }
        )

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
        clearStateFullSubjects()
        super.onDestroy()
    }

    private fun addStateFullSubject(disposable: Disposable) {
        compositeDisposable.remove(disposable)
        compositeDisposable.add(disposable)
    }

    private fun clearStateFullSubjects() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }

    protected abstract fun onDraw(savedInstanceState: Bundle?)

    protected abstract fun onPassViewModel(): BaseViewModel<ViewState>

    protected abstract fun onRender(viewState: ViewState)

}