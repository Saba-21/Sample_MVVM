package com.saba.sampleMVVM.presentation.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.saba.sampleMVVM.R
import com.saba.sampleMVVM.base.presentation.BaseActivity
import com.saba.sampleMVVM.base.presentation.eventHandling.WarningResponse
import com.saba.sampleMVVM.presentation.add.AddingFragmentDirections
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewState, MainViewAction>(R.layout.activity_main, MainViewModel::class) {

    private lateinit var navigationController: NavController

    override fun onDraw(savedInstanceState: Bundle?) {
        navigationController = Navigation.findNavController(this, R.id.mainNavHost)
    }

    override fun onStateReceived(viewState: MainViewState) {
        when (viewState) {
            is MainViewState.OnWarningReceived -> {
                onWarningReceived(viewState.warning)
            }
            is MainViewState.ShowItemDropped -> {
                Toast.makeText(this, "item dropped", Toast.LENGTH_SHORT).show()
            }
            is MainViewState.ShowItemAdded -> {
                Toast.makeText(this, "item saved", Toast.LENGTH_SHORT).show()
            }
            is MainViewState.NavigateToAdding -> {
                this.onBackPressed()
            }
            is MainViewState.NavigateToResult -> {
                navigationController.navigate(AddingFragmentDirections.actionAddingFragmentToResultFragment())
            }
            is MainViewState.ShowLoading -> {
                loader.visibility = View.VISIBLE
            }
            is MainViewState.HideLoading -> {
                loader.visibility = View.GONE
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

}