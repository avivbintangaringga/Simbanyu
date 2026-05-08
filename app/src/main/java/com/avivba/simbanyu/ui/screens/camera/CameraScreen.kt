package com.avivba.simbanyu.ui.screens.camera

import android.Manifest
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.LocalActivity
import androidx.activity.enableEdgeToEdge
import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.compose.viewmodel.koinViewModel
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    viewModel: CameraViewModel = koinViewModel()
) {
    val activity = LocalActivity.current as ComponentActivity
    val state by viewModel.state.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )
        onDispose {
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
                .padding(paddingValues)
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

    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }

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
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        surfaceRequest?.let {
            CameraXViewfinder(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                surfaceRequest = it
            )
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
                        .clickable {
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
                        .background(
                            color = Color.White,
                            shape = CircleShape
                        )
                        .clickable {
                            capturePhoto(
                                context,
                                imageCapture,
                                onSuccess = { file ->
                                    Toast.makeText(
                                        context,
                                        "Foto berhasil diambil: ${file.name}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onError = {
                                    Toast.makeText(
                                        context,
                                        "Gagal mengambil foto",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
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
                        .clickable {
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

private fun capturePhoto(
    context: Context,
    imageCapture: ImageCapture?,
    onSuccess: (File) -> Unit = {},
    onError: (ImageCaptureException) -> Unit = {}
) {
    val capture = imageCapture ?: return

    val timeStamp = System.currentTimeMillis()
    val photoFile = File(context.cacheDir, "IMG_${timeStamp}.jpg")
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    capture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                onSuccess(photoFile)
            }

            override fun onError(e: ImageCaptureException) {
                Log.e("SDKSDK", "Camera Capture Error: ${e.message}")
                e.printStackTrace()
                onError(e)
            }
        }
    )

}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun CameraScreenPreview() {
    CameraContent(
        state = CameraUiState()
    )
}