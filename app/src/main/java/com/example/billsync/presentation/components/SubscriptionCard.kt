package com.example.billsync.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.billsync.R
import com.example.billsync.presentation.model.Subscription

@Composable
fun SubscriptionCard(
    subscription: Subscription,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = RoundedCornerShape(48.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
            ) {
                CircularIconContainer(
                    icon = subscription.brandIcon ?: Icons.Default.ShoppingCart,
                    contentDescription = stringResource(
                        R.string.brand_icon_desc,
                        subscription.brandName
                    ),
                    backgroundColor = subscription.brandColor,
                    modifier = Modifier
                        .size(80.dp)
                )

                Column {
                    Text(
                        text = subscription.brandName,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = subscription.dueDateMessage,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = subscription.displayAmount,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "/${subscription.paymentFrequency}",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SubscriptionCard_Preview() {
    SubscriptionCard(
        Subscription(
            "id",
            "Netflix",
            "$15.99",
            "3 days left",
            "Pending",
            "mo",
            Color.Red,
            null
        )
    )
}