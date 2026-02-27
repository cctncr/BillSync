package com.example.billsync.presentation.screen.create_subscription.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.billsync.R
import com.example.billsync.presentation.common_components.CircularIconContainer
import com.example.billsync.presentation.extensions.toComposeColor
import com.example.billsync.presentation.model.BRAND_COLORS

@Composable
fun BrandColorPicker(
    colors: List<String>,
    selectedColorHex: String,
    onColorSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.brand_color_label),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            colors.forEach { colorHex ->
                val isSelected = colorHex == selectedColorHex

                CircularIconContainer(
                    icon = Icons.Default.Check,
                    contentColor = if (isSelected) Color.White else Color.Transparent,
                    backgroundColor = colorHex.toComposeColor(),
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { onColorSelected(colorHex) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BrandColorPicker_Preview() {
    BrandColorPicker(
        colors = BRAND_COLORS,
        selectedColorHex = BRAND_COLORS.first(),
        onColorSelected = { }
    )
}