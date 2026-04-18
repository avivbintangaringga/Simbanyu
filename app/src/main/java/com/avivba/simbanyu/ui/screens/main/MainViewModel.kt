package com.avivba.simbanyu.ui.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.avivba.simbanyu.ui.base.BaseViewModel

class MainViewModel : BaseViewModel<MainUiState, MainAction, MainEvent>(
    MainUiState()
) {
    override fun onAction(action: MainAction) {
        when (action) {
            is MainAction.ChangeScreen -> {
                Log.d("MainViewModel", "ChangeScreen: ${action.screen}")
                updateState {
                    it.copy(
                        selectedScreen = action.screen
                    )
                }
            }

            MainAction.ShowNotifications -> {

            }
        }
    }
}