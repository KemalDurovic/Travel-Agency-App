package com.example.travelagency.ui.screens.destinations.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.travelagency.model.Destination

@Composable
fun DestinationListItem(destination: Destination, onClick: () -> Unit) {
Card(
        onClick = onClick,
        modifier = Modifier
                .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
shape = RoundedCornerShape(12.dp),
elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
Row(
        modifier = Modifier.padding(12.dp),
verticalAlignment = Alignment.CenterVertically
        ) {
Box(
        modifier = Modifier.size(64.dp),
contentAlignment = Alignment.Center
            ) {
Text(text = "✈️", style = MaterialTheme.typography.headlineMedium)
            }

Spacer(modifier = Modifier.width(12.dp))

Column(modifier = Modifier.weight(1f)) {
Text(
        text = destination.name,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
)
Row(verticalAlignment = Alignment.CenterVertically) {
    Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    )
    Text(
            text = destination.country,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    )
}
Row(verticalAlignment = Alignment.CenterVertically) {
    Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    )
    Spacer(modifier = Modifier.width(2.dp))
    Text(
            text = "${destination.duration} days",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    )
}
            }

Column(horizontalAlignment = Alignment.End) {
    Text(
            text = "$${destination.price}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
    )
    Text(
            text = "per person",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    )
}
        }
                }
                }

@Composable
fun EmptyDestinationsMessage() {
    Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "🌐", style = MaterialTheme.typography.displayLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                    text = "No destinations yet",
                    style = MaterialTheme.typography.titleMedium
            )
            Text(
                    text = "Check back soon for new trips!",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}