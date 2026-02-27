package com.example.billsync.presentation.common_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CircularIconContainer(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    backgroundColor: Color? = null,
    contentColor: Color? = null
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = backgroundColor ?: MaterialTheme.colorScheme.secondaryContainer,
        contentColor = contentColor ?: MaterialTheme.colorScheme.onSecondaryContainer
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize(0.6f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CircularIconContainer_Preview() {
    CircularIconContainer(
        icon = Icons.Default.Person,
        backgroundColor = Color.Yellow,
        contentColor = Color.Red,
        modifier = Modifier.size(100.dp)
    )
}