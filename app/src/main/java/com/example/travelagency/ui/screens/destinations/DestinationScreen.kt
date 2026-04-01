package com.example.travelagency.ui.screens.destinations

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.travelagency.model.Destination
import com.example.travelagency.model.sampleDestinations
import com.example.travelagency.ui.screens.destinations.components.DestinationListItem
import com.example.travelagency.ui.screens.destinations.components.EmptyDestinationsMessage
import com.example.travelagency.viewmodel.HomeViewModel

@Composable
fun DestinationsScreen(
        viewModel: HomeViewModel,
        onDestinationClick: (Int) -> Unit
) {
val destinations by viewModel.destinations.collectAsStateWithLifecycle()

Column(modifier = Modifier.fillMaxSize()) {
Text(
        text = "All Destinations",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
        )

                if (destinations.isEmpty()) {
EmptyDestinationsMessage()
        } else {
LazyColumn(modifier = Modifier.fillMaxSize()) {
items(destinations) { destination ->
        DestinationListItem(
                destination = destination,
                onClick = { onDestinationClick(destination.id) }
        )
}
item { Spacer(modifier = Modifier.height(16.dp)) }
            }
                    }
                    }
                    }

@Composable
fun DestinationDetailScreen(
        destinationId: Int,
        onBookClick: (Int) -> Unit,
onBack: () -> Unit
) {
val destination = sampleDestinations.find { it.id == destinationId }

        if (destination == null) {
Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
Text("Destination not found")
        }
                return
                }

Column(
        modifier = Modifier
                .fillMaxSize()
            .padding(16.dp)
    ) {
OutlinedButton(onClick = onBack) {
    Text("← Back")
}

Spacer(modifier = Modifier.height(16.dp))

Box(
        modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
Text(text = "🌍", style = MaterialTheme.typography.displayLarge)
        }

Spacer(modifier = Modifier.height(16.dp))

Text(
        text = destination.name,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold
)
Text(
        text = destination.country,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

Spacer(modifier = Modifier.height(12.dp))

Text(
        text = destination.description,
        style = MaterialTheme.typography.bodyMedium
)

Spacer(modifier = Modifier.height(16.dp))

Row(
        modifier = Modifier.fillMaxWidth(),
horizontalArrangement = Arrangement.SpaceBetween
        ) {
InfoChip(label = "Duration", value = "${destination.duration} days")
InfoChip(label = "Price", value = "From $${destination.price}")
        }

Spacer(modifier = Modifier.weight(1f))

Button(
        onClick = { onBookClick(destination.id) },
modifier = Modifier.fillMaxWidth()
        ) {
Text("Book This Trip")
        }
                }
                }

@Composable
fun InfoChip(label: String, value: String) {
    Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
            )
            Text(
                    text = value,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}