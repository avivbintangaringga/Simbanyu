package com.avivba.simbanyu.di

import com.avivba.simbanyu.ui.screens.billdetails.BillDetailsViewModel
import com.avivba.simbanyu.ui.screens.billhistory.BillHistoryViewModel
import com.avivba.simbanyu.ui.screens.dashboard.DashboardViewModel
import com.avivba.simbanyu.ui.screens.invoice.InvoiceViewModel
import com.avivba.simbanyu.ui.screens.login.LoginViewModel
import com.avivba.simbanyu.ui.screens.main.MainViewModel
import com.avivba.simbanyu.ui.screens.operator.announcement.AnnouncementViewModel
import com.avivba.simbanyu.ui.screens.operator.dashboard.OperatorDashboardViewModel
import com.avivba.simbanyu.ui.screens.operator.main.OperatorMainViewModel
import com.avivba.simbanyu.ui.screens.operator.subsribers.SubscribersViewModel
import com.avivba.simbanyu.ui.screens.paymentmethod.PaymentMethodViewModel
import com.avivba.simbanyu.ui.screens.profile.ProfileViewModel
import com.avivba.simbanyu.ui.screens.report.ReportViewModel
import com.avivba.simbanyu.ui.screens.usagehistory.UsageHistoryViewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

val viewModelModule = module {
    viewModel<LoginViewModel>()
    viewModel<MainViewModel>()

    viewModel<DashboardViewModel>()
    viewModel<BillHistoryViewModel>()
    viewModel<UsageHistoryViewModel>()
    viewModel<ReportViewModel>()
    viewModel<ProfileViewModel>()
    viewModel<InvoiceViewModel>()
    viewModel<PaymentMethodViewModel>()
    viewModel<BillDetailsViewModel>()

    // OPERATOR
    viewModel<OperatorMainViewModel>()
    viewModel<OperatorDashboardViewModel>()
    viewModel<AnnouncementViewModel>()
    viewModel<SubscribersViewModel>()
}