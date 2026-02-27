package com.example.billsync.presentation.screen.subscriptions.components

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.billsync.R
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.presentation.common_components.CircularIconContainer
import com.example.billsync.presentation.extensions.getStatusColor
import com.example.billsync.presentation.extensions.toAbbreviation
import com.example.billsync.presentation.extensions.toDisplayName
import com.example.billsync.presentation.extensions.toDueDateMessage
import com.example.billsync.presentation.model.Subscription
import java.time.LocalDate

@Composable
fun SubscriptionCard(
    subscription: Subscription,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    currentDate: LocalDate = LocalDate.now()
) {
    val borderColor = if (subscription.status == BillStatus.OVERDUE) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
    }

    Surface(
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, borderColor),
        color = MaterialTheme.colorScheme.surface,
        onClick = onClick,
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
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                CircularIconContainer(
                    icon = subscription.brandIcon ?: Icons.Default.ShoppingCart,
                    contentDescription = stringResource(
                        R.string.brand_icon_desc,
                        subscription.brandName
                    ),
                    backgroundColor = subscription.brandColor,
                    modifier = Modifier
                        .size(60.dp)
                )

                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = subscription.brandName,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = subscription.dueDate.toDueDateMessage(currentDate),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text(
                        text = subscription.displayAmount,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                    Text(
                        text = "/${subscription.paymentFrequency.toAbbreviation()}",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1
                    )
                }

                StatusBadge(subscription.status)
            }
        }
    }
}

@Composable
fun StatusBadge(status: BillStatus, modifier: Modifier = Modifier) {
    Surface(
        color = status.getStatusColor().copy(alpha = 0.15f),
        contentColor = status.getStatusColor(),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
    ) {
        Text(
            text = status.toDisplayName(),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Preview
@Composable
fun StatusBadge_Pending_Preview() {
    StatusBadge(BillStatus.PENDING)
}

@Preview
@Composable
fun StatusBadge_Paid_Preview() {
    StatusBadge(BillStatus.PAID)
}

@Preview
@Composable
fun StatusBadge_Overdue_Preview() {
    StatusBadge(BillStatus.OVERDUE)
}

@Preview(showBackground = true)
@Composable
fun SubscriptionCard_Pending_Preview() {
    SubscriptionCard(
        subscription = Subscription(
            id = "1",
            brandName = "Netflix",
            displayAmount = "$15.99",
            dueDate = LocalDate.now().plusDays(3),
            status = BillStatus.PENDING,
            paymentFrequency = PaymentFrequency.MONTHLY,
            brandColor = Color.Red,
            brandIcon = null
        ),
        currentDate = LocalDate.now(),
        onClick = { }
    )
}

@Preview(showBackground = true)
@Composable
fun SubscriptionCard_Overdue_Preview() {
    SubscriptionCard(
        subscription = Subscription(
            id = "2",
            brandName = "Spotify",
            displayAmount = "$7.99",
            dueDate = LocalDate.now().minusDays(2),
            status = BillStatus.OVERDUE,
            paymentFrequency = PaymentFrequency.MONTHLY,
            brandColor = Color.Green,
            brandIcon = null
        ),
        currentDate = LocalDate.now(),
        onClick = { }
    )
}

@Preview(showBackground = true)
@Composable
fun SubscriptionCard_Paid_Preview() {
    SubscriptionCard(
        subscription = Subscription(
            id = "3",
            brandName = "Very Very Long Brand Nameeeeeeeeee",
            displayAmount = "$6477.00",
            dueDate = LocalDate.now().plusDays(8),
            status = BillStatus.PAID,
            paymentFrequency = PaymentFrequency.YEARLY,
            brandColor = Color.Cyan,
            brandIcon = null
        ),
        currentDate = LocalDate.now(),
        onClick = { }
    )
}