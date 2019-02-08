package com.saba.sample_mvvm.base.structure

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment() {

    protected abstract fun renderView(view: View?, savedInstanceState: Bundle?)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderView(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View? = null
        val layoutResourceId = javaClass.getAnnotation(LayoutResourceId::class.java)
        if (layoutResourceId != null) {
            view = inflater.inflate(layoutResourceId.value, container, false)
        }
        return view
    }

}