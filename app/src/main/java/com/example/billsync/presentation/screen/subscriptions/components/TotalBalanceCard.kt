package com.example.billsync.presentation.screen.subscriptions.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.billsync.R
import com.example.billsync.presentation.model.LinearGradientSpacerOrientation

@Composable
fun TotalBalanceCard(
    totalBalance: String,
    avgDailyCost: String,
    balanceLabel: String,
    activeSubsCount: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(48.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(28.dp)
        ) {
            BalanceCardHeader(
                balanceLabel,
                totalBalance
            )

            LinearGradientSpacer(
                orientation = LinearGradientSpacerOrientation.HORIZONTAL,
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(vertical = 24.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                BalanceCardStatItem(
                    label = stringResource(R.string.avg_daily_cost),
                    value = avgDailyCost
                )

                LinearGradientSpacer(
                    orientation = LinearGradientSpacerOrientation.VERTICAL,
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                BalanceCardStatItem(
                    label = stringResource(R.string.active_subs),
                    value = activeSubsCount.toString()
                )
            }
        }
    }
}

@Composable
private fun LinearGradientSpacer(
    orientation: LinearGradientSpacerOrientation,
    thickness: Dp,
    color: Color,
    modifier: Modifier = Modifier
) {
    val isVertical = orientation == LinearGradientSpacerOrientation.VERTICAL

    Spacer(
        modifier = if (isVertical) {
            modifier
                .width(thickness)
                .fillMaxHeight()
        } else {
            modifier
                .height(thickness)
                .fillMaxWidth()
        }.drawWithCache {
            val brush = if (isVertical) {
                Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        color,
                        Color.Transparent
                    )
                )
            } else {
                Brush.horizontalGradient(
                    listOf(
                        Color.Transparent,
                        color,
                        Color.Transparent
                    )
                )
            }
            onDrawBehind {
                drawRect(brush)
            }
        }
    )
}

@Composable
private fun BalanceCardHeader(
    balanceLabel: String,
    totalBalance: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = balanceLabel,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = totalBalance,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun BalanceCardStatItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TotalBalanceCard_Preview() {
    TotalBalanceCard(
        "$145.00",
        "$4.83",
        "TOTAL MONTHLY",
        12
    )
}