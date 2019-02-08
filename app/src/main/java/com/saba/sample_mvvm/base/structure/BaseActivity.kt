package com.saba.sample_mvvm.base.structure

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun renderView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutResourceId = javaClass.getAnnotation(LayoutResourceId::class.java)
        if (layoutResourceId != null)
            setContentView(layoutResourceId.value)
        renderView(savedInstanceState)
    }

}