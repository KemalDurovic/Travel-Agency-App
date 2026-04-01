package com.example.travelagency.ui.screens.booking.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BookingTextField(
        value: String,
        onValueChange: (String) -> Unit,
label: String,
placeholder: String,
errorMessage: String? = null,
modifier: Modifier = Modifier
) {
Column(modifier = modifier.fillMaxWidth()) {
OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
placeholder = { Text(placeholder) },
isError = errorMessage != null,
modifier = Modifier.fillMaxWidth(),
singleLine = true
        )
        if (errorMessage != null) {
Text(
        text = errorMessage,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
                    }
                    }
                    }

@Composable
fun TravelerCounter(count: Int, onIncrement: () -> Unit, onDecrement: () -> Unit) {
Row(
        modifier = Modifier.fillMaxWidth(),
verticalAlignment = Alignment.CenterVertically,
horizontalArrangement = Arrangement.SpaceBetween
    ) {
Text(
        text = "Number of Travelers",
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Medium
)
Row(verticalAlignment = Alignment.CenterVertically) {
    OutlinedButton(
            onClick = onDecrement,
            enabled = count > 1,
            modifier = Modifier.size(36.dp),
            contentPadding = PaddingValues(0.dp)
    ) {
        Text("-")
    }
    Text(
            text = count.toString(),
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
    )
    OutlinedButton(
            onClick = onIncrement,
            enabled = count < 10,
            modifier = Modifier.size(36.dp),
            contentPadding = PaddingValues(0.dp)
    ) {
        Text("+")
    }
}
    }
            }

@Composable
fun SuccessDialog(onDismiss: () -> Unit) {
AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Booking Confirmed! 🎉") },
text = { Text("Your trip has been booked. We will send you a confirmation email shortly.") },
confirmButton = {
Button(onClick = onDismiss) {
    Text("Back to Home")
}
        }
                )
                }