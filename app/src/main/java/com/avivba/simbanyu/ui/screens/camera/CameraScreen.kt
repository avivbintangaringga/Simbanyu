package com.avivba.simbanyu.ui.screens.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Build
import android.util.Log
import android.view.OrientationEventListener
import android.view.Surface
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.LocalActivity
import androidx.activity.enableEdgeToEdge
import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.compose.viewmodel.koinActivityViewModel
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@SuppressLint("SourceLockedOrientationActivity")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    viewModel: CameraViewModel = koinActivityViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val activity = LocalActivity.current as ComponentActivity
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            is CameraEvent.ImageCaptured -> {
                viewModel.onAction(CameraAction.SetCaptureState(false))
                onNavigateBack()
            }
        }
    }

    DisposableEffect(Unit) {
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )
        onDispose {
            activity.requestedOrientation = originalOrientation
            activity.enableEdgeToEdge()
        }
    }

    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )

    LaunchedEffect(Unit) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = Color.Black
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (cameraPermissionState.status.isGranted) {
                CameraContent(
                    state = state,
                    onAction = viewModel::onAction
                )

            } else {
                SimbanyuText(
                    modifier = Modifier
                        .padding(
                            horizontal = SimbanyuTheme.padding.medium
                        ),
                    text = "Izinkan aplikasi mengakses kamera untuk melanjutkan",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun CameraContent(
    modifier: Modifier = Modifier,
    state: CameraUiState,
    onAction: (CameraAction) -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    var rotationDegrees by remember { mutableFloatStateOf(0f) }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }

    DisposableEffect(context) {
        val listener = object : OrientationEventListener(context) {
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) return
                val newRotation = when (orientation) {
                    in 45..134 -> 270f
                    in 135..224 -> 180f
                    in 225..314 -> 90f
                    else -> 0f
                }
                if (rotationDegrees != newRotation) {
                    rotationDegrees = newRotation
                }
            }
        }
        listener.enable()
        onDispose {
            listener.disable()
        }
    }

    val animatedRotation by animateFloatAsState(targetValue = rotationDegrees)

    val surfaceRequests = remember { MutableStateFlow<SurfaceRequest?>(null) }
    val surfaceRequest by surfaceRequests.collectAsStateWithLifecycle(null)

    LaunchedEffect(state.isFrontCamera, state.isUsingFlash) {
        val provider = ProcessCameraProvider.awaitInstance(context)
        val preview = androidx.camera.core.Preview.Builder()
            .build().apply {
                setSurfaceProvider { req ->
                    surfaceRequests.value = req
                }
            }

        val cameraSelector = if (state.isFrontCamera) {
            CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            CameraSelector.DEFAULT_BACK_CAMERA
        }

        imageCapture = ImageCapture.Builder()
            .setFlashMode(
                if (state.isUsingFlash)
                    ImageCapture.FLASH_MODE_ON
                else ImageCapture.FLASH_MODE_OFF
            )
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
            .build()

        provider.unbindAll()
        provider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        surfaceRequest?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                CameraXViewfinder(
                    modifier = Modifier.fillMaxSize(),
                    surfaceRequest = it
                )

                if (state.isCapturing) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.White
                        )
                    }
                }
            }
        } ?: @Composable {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(
                        horizontal = SimbanyuTheme.padding.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                SimbanyuText(
                    modifier = Modifier,
                    text = "Memuat kamera...",
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = SimbanyuTheme.padding.large,
                        vertical = SimbanyuTheme.padding.extraExtraLarge
                    )
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    val icon = if (state.isUsingFlash) {
                        R.drawable.ic_flashlight_off_filled
                    } else {
                        R.drawable.ic_flashlight_on_filled
                    }

                    Icon(
                        painter = painterResource(icon),
                        modifier = Modifier
                            .clip(
                                shape = CircleShape
                            )
                            .graphicsLayer {
                                rotationZ = animatedRotation
                            }
                            .clickable(enabled = !state.isCapturing) {
                                onAction(CameraAction.ToggleFlash)
                            }
                            .padding(
                                SimbanyuTheme.padding.large
                            ),
                        tint = Color.White,
                        contentDescription = null
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_photo_camera_filled),
                        modifier = Modifier
                            .graphicsLayer {
                                rotationZ = animatedRotation
                            }
                            .background(
                                color = Color.White,
                                shape = CircleShape
                            )
                            .clickable(enabled = !state.isCapturing) {
                                onAction(CameraAction.SetCaptureState(true))
                                scope.launch {
                                    try {
                                        val file = capturePhoto(
                                            context = context,
                                            imageCapture = imageCapture,
                                            rotationDegrees = rotationDegrees,
                                            isFrontCamera = state.isFrontCamera
                                        )
                                        onAction(CameraAction.ImageCaptured(file))
                                    } catch (_: Exception) {
                                        onAction(CameraAction.SetCaptureState(false))
                                        Toast.makeText(
                                            context,
                                            "Gagal mengambil foto",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                            .padding(
                                SimbanyuTheme.padding.large
                            ),
                        tint = Color.Black,
                        contentDescription = null
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_cameraswitch_filled),
                        modifier = Modifier
                            .clip(
                                shape = CircleShape
                            )
                            .graphicsLayer {
                                rotationZ = animatedRotation
                            }
                            .clickable(enabled = !state.isCapturing) {
                                onAction(CameraAction.ToggleCamera)
                            }
                            .padding(
                                SimbanyuTheme.padding.large
                            ),
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

private suspend fun capturePhoto(
    context: Context,
    imageCapture: ImageCapture?,
    rotationDegrees: Float,
    isFrontCamera: Boolean
): File {
    return suspendCancellableCoroutine { continuation ->
        val capture = imageCapture ?: run {
            continuation.resumeWithException(IllegalStateException("Kamera belum siap"))
            return@suspendCancellableCoroutine
        }

        val timeStamp = System.currentTimeMillis()
        val photoFile = File(context.cacheDir, "IMG_${timeStamp}.webp")

        capture.takePicture(
            Dispatchers.IO.asExecutor(),
            object : ImageCapture.OnImageCapturedCallback() {

                override fun onCaptureSuccess(image: ImageProxy) {
                    val originalBitmap = image.use { it.toBitmap() }

                    val matrix = Matrix().apply {
                        if (isFrontCamera) {
                            postRotate(270f + rotationDegrees)
                            postScale(-1f, 1f, originalBitmap.width / 2f, originalBitmap.height / 2f)
                        } else {
                            postRotate(90f - rotationDegrees)
                        }
                    }

                    val finalBitmap = Bitmap.createBitmap(
                        originalBitmap,
                        0,
                        0,
                        originalBitmap.width,
                        originalBitmap.height,
                        matrix,
                        true
                    )

                    try {
                        photoFile.outputStream().use { out ->
                            val format = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                Bitmap.CompressFormat.WEBP_LOSSY
                            } else {
                                @Suppress("DEPRECATION")
                                Bitmap.CompressFormat.WEBP
                            }
                            finalBitmap.compress(format, 80, out)
                        }
                        continuation.resume(photoFile)
                    } catch (e: Exception) {
                        Log.e("SDKSDK", "Error saving image: ${e.message}")
                        continuation.resumeWithException(e)
                    } finally {
                        originalBitmap.recycle()
                        if (finalBitmap != originalBitmap) {
                            finalBitmap.recycle()
                        }
                    }
                }

                override fun onError(e: ImageCaptureException) {
                    Log.e("SDKSDK", "Camera capture error: ${e.message}")
                    continuation.resumeWithException(e)
                }
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun CameraScreenPreview() {
    CameraContent(
        state = CameraUiState()
    )
}