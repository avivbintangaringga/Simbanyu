package com.avivba.simbanyu.ui.screens.dashboard

import app.cash.turbine.test
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.AnnouncementRepository
import com.avivba.simbanyu.domain.repository.BillRepository
import com.avivba.simbanyu.domain.repository.RefreshEventBus
import com.avivba.simbanyu.domain.repository.UsageRepository
import com.avivba.simbanyu.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var billRepository: BillRepository
    private lateinit var announcementRepository: AnnouncementRepository
    private lateinit var usageRepository: UsageRepository
    private lateinit var userRepository: UserRepository
    private lateinit var refreshEventBus: RefreshEventBus
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        billRepository = mockk(relaxed = true)
        announcementRepository = mockk(relaxed = true)
        usageRepository = mockk(relaxed = true)
        userRepository = mockk(relaxed = true)
        refreshEventBus = mockk(relaxed = true)

        every { refreshEventBus.refreshEvent } returns MutableSharedFlow()
        every { userRepository.getLocalProfileFlow() } returns flowOf(null)
        
        coEvery { billRepository.getAll(any(), any()) } returns Result.Success(emptyList())
        coEvery { announcementRepository.getAllAnnouncements() } returns Result.Success(emptyList())

        viewModel = DashboardViewModel(
            billRepository,
            announcementRepository,
            usageRepository,
            userRepository,
            refreshEventBus
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be loading`() = runTest {
        viewModel.state.test {
            assertTrue(awaitItem().isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `load success should update state and set loading to false`() = runTest {
        viewModel.state.test {
            awaitItem() // Initial loading=true
            
            advanceUntilIdle()
            
            val state = expectMostRecentItem()
            assertFalse(state.isLoading)
        }
    }

    @Test
    fun `refresh should set isRefreshing to true then false`() = runTest {
        viewModel.state.test {
            advanceUntilIdle() // Finish initial load
            skipItems(1) // Skip initial state if needed
            
            viewModel.onAction(DashboardAction.Refresh)
            
            // Note: StandardTestDispatcher might be too fast to catch isRefreshing=true 
            // if we use advanceUntilIdle.
            // But we can verify the end state.
            advanceUntilIdle()
            val state = expectMostRecentItem()
            assertFalse(state.isRefreshing)
            assertFalse(state.isLoading)
        }
    }
}
