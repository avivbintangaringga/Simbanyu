package com.avivba.simbanyu.di

import com.avivba.simbanyu.ui.screens.billhistory.BillHistoryViewModel
import com.avivba.simbanyu.ui.screens.dashboard.DashboardViewModel
import com.avivba.simbanyu.ui.screens.login.LoginViewModel
import com.avivba.simbanyu.ui.screens.main.MainViewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

val viewModelModule = module {
    viewModel<LoginViewModel>()
    viewModel<DashboardViewModel>()
    viewModel<MainViewModel>()
    viewModel<BillHistoryViewModel>()
}