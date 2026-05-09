package com.avivba.simbanyu.ui.components.imageview

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.conditional

@Composable
fun ImageView(
    modifier: Modifier = Modifier,
    model: Any,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.FillWidth,
    loading: @Composable () -> Unit = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = SimbanyuTheme.colors.brand
            )
        }
    },
    error: @Composable () -> Unit = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .height(60.dp),
                painter = painterResource(R.drawable.ic_warning_filled),
                contentDescription = null,
            )
        }
    },
    onClick: (() -> Unit)? = null
) {
    SubcomposeAsyncImage(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                SimbanyuTheme.shapes.large
            )
            .conditional(onClick != null) {
                clickable {
                    onClick?.invoke()
                }
            },
        model = model,
        contentDescription = contentDescription,
        contentScale = contentScale,
        loading = { loading() },
        error = { error() },
        onError = {
            Log.d("SDKSDK", "IMAGEVIEW: ${model::class}")
            it.result.throwable.printStackTrace()
        }
    )
}