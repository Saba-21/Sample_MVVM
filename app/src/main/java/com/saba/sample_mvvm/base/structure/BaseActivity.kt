package com.saba.sample_mvvm.base.structure

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.saba.sample_mvvm.base.annotations.LayoutResourceId

abstract class BaseActivity<ViewState : BaseViewState> : AppCompatActivity() {

    lateinit var baseViewModel: BaseViewModel<ViewState>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutResourceId = javaClass.getAnnotation(LayoutResourceId::class.java)
        if (layoutResourceId != null)
            setContentView(layoutResourceId.value)

        onDraw(savedInstanceState)

        baseViewModel = onPassViewModel()

        baseViewModel.getStateFullObservable().observe(this, Observer {
            it?.let { viewState ->
                onRender(viewState)
            }
        })

        baseViewModel.getStateAwareObservable().observe(this, Observer {
            it?.let { viewState ->
                onRender(viewState)
            }
        })
    }

    protected abstract fun onDraw(savedInstanceState: Bundle?)

    protected abstract fun onPassViewModel(): BaseViewModel<ViewState>

    protected abstract fun onRender(viewState: ViewState)

}