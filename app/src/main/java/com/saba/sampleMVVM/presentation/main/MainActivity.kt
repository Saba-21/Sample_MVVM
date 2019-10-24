package com.saba.sampleMVVM.presentation.main

import android.os.Bundle
import android.widget.Toast
import com.saba.sampleMVVM.R
import com.saba.sampleMVVM.base.presentation.ACTIVITY
import com.saba.sampleMVVM.base.presentation.BaseActivity
import com.saba.sampleMVVM.base.presentation.eventHandling.WarningResponse
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getOrCreateScope
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewState, MainViewAction>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModel()
    override fun onPassViewModel() = viewModel

    override fun onStateReceived(viewState: MainViewState) {
        when (viewState) {
            is MainViewState.OnWarningReceived -> {
                onWarningReceived(viewState.warning)
            }
        }
    }

    private fun onWarningReceived(errorState: WarningResponse) {
        when (errorState) {
            WarningResponse.FAIL -> {
                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
            }
            WarningResponse.UNAUTHORISED -> {
                Toast.makeText(this, "401", Toast.LENGTH_SHORT).show()
            }
            WarningResponse.NOT_FOUNT -> {
                Toast.makeText(this, "404", Toast.LENGTH_SHORT).show()
            }
            WarningResponse.OFFLINE -> {
                Toast.makeText(this, "offline", Toast.LENGTH_SHORT).show()
            }
            WarningResponse.POOR_CONNECTION -> {
                Toast.makeText(this, "poor_connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDraw(savedInstanceState: Bundle?) {
        bindScope(getOrCreateScope(ACTIVITY))
    }

}