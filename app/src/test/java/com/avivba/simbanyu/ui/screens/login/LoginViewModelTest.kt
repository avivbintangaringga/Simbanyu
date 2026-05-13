package com.avivba.simbanyu.ui.screens.login

import android.util.Log
import app.cash.turbine.test
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.User
import com.avivba.simbanyu.domain.repository.AuthRepository
import com.avivba.simbanyu.domain.repository.FcmTokenRepository
import io.mockk.every
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var authRepository: AuthRepository
    private lateinit var fcmTokenRepository: FcmTokenRepository
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        
        // Mock Android Log
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
        
        authRepository = mockk(relaxed = true)
        fcmTokenRepository = mockk(relaxed = true)
        
        coEvery { fcmTokenRepository.getFcmToken() } returns "fake_token"
        coEvery { fcmTokenRepository.save(any()) } returns Result.Success(Unit)
        
        viewModel = LoginViewModel(authRepository, fcmTokenRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login success for subscriber should navigate to subscriber dashboard`() = runTest {
        val user = User(
            id = "1",
            username = "user",
            name = "User",
            role = User.UserRole.SUBSCRIBER,
            phoneNumber = "",
            photoImageUrl = ""
        )
        coEvery { authRepository.login(any(), any()) } returns Result.Success(user)

        viewModel.event.test {
            viewModel.onAction(LoginAction.Login)
            advanceUntilIdle()
            assertEquals(LoginEvent.GoToSubscriberDashboard, awaitItem())
        }
    }

    @Test
    fun `login success for operator should navigate to operator dashboard`() = runTest {
        val user = User(
            id = "2",
            username = "op",
            name = "Op",
            role = User.UserRole.OPERATOR,
            phoneNumber = "",
            photoImageUrl = ""
        )
        coEvery { authRepository.login(any(), any()) } returns Result.Success(user)

        viewModel.event.test {
            viewModel.onAction(LoginAction.Login)
            advanceUntilIdle()
            assertEquals(LoginEvent.GoToOperatorDashboard, awaitItem())
        }
    }

    @Test
    fun `login failure with unauthorized should show error message`() = runTest {
        coEvery { authRepository.login(any(), any()) } returns Result.Error(DataError.NetworkError.UNAUTHORIZED)

        viewModel.state.test {
            awaitItem() // Initial state
            
            viewModel.onAction(LoginAction.Login)
            
            advanceUntilIdle()
            
            val lastState = expectMostRecentItem()
            
            assertEquals("Username atau password salah", lastState.errorMessage)
            assertFalse(lastState.isLoading)
        }
    }

    @Test
    fun `toggle password visibility should update state`() = runTest {
        viewModel.state.test {
            awaitItem() // initial
            
            viewModel.onAction(LoginAction.TogglePasswordVisibility)
            advanceUntilIdle()
            assertTrue(awaitItem().isPasswordVisible)
            
            viewModel.onAction(LoginAction.TogglePasswordVisibility)
            advanceUntilIdle()
            assertFalse(awaitItem().isPasswordVisible)
        }
    }
}
