package com.saba.sample_mvvm.base.structure

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import com.saba.sample_mvvm.custom.ACTIVITY
import org.koin.android.ext.android.get
import org.koin.android.scope.ext.android.getScope
import org.koin.core.parameter.parametersOf

abstract class BaseFragment : Fragment() {

    protected abstract fun onDraw(view: View?, savedInstanceState: Bundle?, navigationController: NavController)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onDraw(
            view,
            savedInstanceState,
            get(scope = getScope(ACTIVITY)) {
                parametersOf(activity!!)
            })
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