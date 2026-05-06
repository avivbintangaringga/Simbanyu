package com.avivba.simbanyu.ui.components.emptystate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    message: String,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = SimbanyuTheme.padding.extraExtraLarge
            ),
        contentAlignment = Alignment.Center
    ) {
        SimbanyuText(
            text = message,
            color = SimbanyuTheme.colors.statusNeutral,
            style = SimbanyuTheme.typography.bodyStandard.copy(
                fontStyle = FontStyle.Italic,
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyStatePreview() {
    EmptyState(
        message = "Tidak ada pemberitahuan"
    )
}