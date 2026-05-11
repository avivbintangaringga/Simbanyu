package com.avivba.simbanyu.ui.screens.paymentsuccess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun PaymentSuccessScreen(
    modifier: Modifier = Modifier,
    onNavigateToBillHistory: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(SimbanyuTheme.padding.large),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SimbanyuText(
                text = "Pembayaran Berhasil!",
                style = SimbanyuTheme.typography.displayMedium,
                color = SimbanyuTheme.colors.brand,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            SimbanyuText(
                text = "Terima kasih telah melakukan pembayaran tepat waktu.",
                style = SimbanyuTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            SimbanyuButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = SimbanyuTheme.colors.brush.brandVertical,
                        shape = SimbanyuTheme.shapes.extraLarge
                    ),
                text = "Kembali ke Riwayat Tagihan",
                onClick = onNavigateToBillHistory
            )
        }
    }
}
