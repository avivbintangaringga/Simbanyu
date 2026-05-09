package com.avivba.simbanyu.di

import com.avivba.simbanyu.ui.activities.MainActivityViewModel
import com.avivba.simbanyu.ui.screens.announcement.AnnouncementViewModel
import com.avivba.simbanyu.ui.screens.billdetails.BillDetailsViewModel
import com.avivba.simbanyu.ui.screens.billhistory.BillHistoryViewModel
import com.avivba.simbanyu.ui.screens.camera.CameraViewModel
import com.avivba.simbanyu.ui.screens.dashboard.DashboardViewModel
import com.avivba.simbanyu.ui.screens.invoice.InvoiceViewModel
import com.avivba.simbanyu.ui.screens.login.LoginViewModel
import com.avivba.simbanyu.ui.screens.main.MainViewModel
import com.avivba.simbanyu.ui.screens.operator.announcement.OperatorAnnouncementViewModel
import com.avivba.simbanyu.ui.screens.operator.dashboard.OperatorDashboardViewModel
import com.avivba.simbanyu.ui.screens.operator.main.OperatorMainViewModel
import com.avivba.simbanyu.ui.screens.operator.map.MapViewModel
import com.avivba.simbanyu.ui.screens.operator.meterrecord.MeterRecordViewModel
import com.avivba.simbanyu.ui.screens.operator.report.OperatorReportViewModel
import com.avivba.simbanyu.ui.screens.operator.subscriberdetail.SubscriberDetailsViewModel
import com.avivba.simbanyu.ui.screens.operator.subsribers.SubscribersViewModel
import com.avivba.simbanyu.ui.screens.paymentmethod.PaymentMethodViewModel
import com.avivba.simbanyu.ui.screens.profile.ProfileViewModel
import com.avivba.simbanyu.ui.screens.report.ReportViewModel
import com.avivba.simbanyu.ui.screens.usagehistory.UsageHistoryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel(get(), get(), get()) }

    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { CameraViewModel() }

    viewModel { DashboardViewModel(get(), get(), get(), get()) }
    viewModel { BillHistoryViewModel(get()) }
    viewModel { UsageHistoryViewModel(get(), get()) }
    viewModel { ReportViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { InvoiceViewModel(get()) }
    viewModel { PaymentMethodViewModel() }
    viewModel { BillDetailsViewModel(get()) }
    viewModel { AnnouncementViewModel(get()) }

    // OPERATOR
    viewModel { OperatorMainViewModel(get()) }
    viewModel { OperatorDashboardViewModel(get(), get()) }
    viewModel { OperatorAnnouncementViewModel(get()) }
    viewModel { SubscribersViewModel(get()) }
    viewModel { SubscriberDetailsViewModel(get(), get(), get(), get()) }
    viewModel { OperatorReportViewModel(get()) }
    viewModel { MeterRecordViewModel(get()) }
    viewModel { MapViewModel(get()) }
}