package com.example.billsync.presentation.screen.subscriptions.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.billsync.R
import com.example.billsync.presentation.common_components.CircularIconContainer

@Composable
fun ProfileCard(
    fullName: String,
    modifier: Modifier = Modifier,
    profileIcon: ImageVector? = null,
    greeting: String = stringResource(R.string.greeting_welcome_back),
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        CircularIconContainer(
            icon = profileIcon ?: Icons.Default.Person,
            contentDescription = stringResource(R.string.profile_icon),
            modifier = Modifier.size(60.dp)
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = greeting,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.titleSmall,
            )
            Text(
                text = fullName,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileCard_Preview() {
    ProfileCard(
        fullName = "Alex Morgan",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}