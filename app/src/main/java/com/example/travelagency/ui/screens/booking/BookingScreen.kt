package com.example.travelagency.ui.screens.booking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.travelagency.model.sampleDestinations
import com.example.travelagency.ui.screens.booking.components.BookingTextField
import com.example.travelagency.ui.screens.booking.components.SuccessDialog
import com.example.travelagency.ui.screens.booking.components.TravelerCounter
import com.example.travelagency.viewmodel.BookingViewModel

@Composable
fun BookingScreen(
    viewModel: BookingViewModel,
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val form by viewModel.form.collectAsStateWithLifecycle()
    val nameError by viewModel.nameError.collectAsStateWithLifecycle()
    val emailError by viewModel.emailError.collectAsStateWithLifecycle()
    val bookingSuccess by viewModel.bookingSuccess.collectAsStateWithLifecycle()

    val destination = sampleDestinations.find { it.id == form.destinationId }

    if (bookingSuccess) {
        SuccessDialog(onDismiss = {
            viewModel.resetBooking()
            onSuccess()
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(onClick = onBack) {
            Text("← Back")
        }

        Text(
            text = "Book Your Trip",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        if (destination != null) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Booking: ${destination.name}, ${destination.country}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "From $${destination.price} per person • ${destination.duration} days",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }
        }

        BookingTextField(
            value = form.fullName,
            onValueChange = { viewModel.onNameChange(it) },
            label = "Full Name",
            placeholder = "Enter your full name",
            errorMessage = nameError
        )

        BookingTextField(
            value = form.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = "Email Address",
            placeholder = "Enter your email",
            errorMessage = emailError
        )

        TravelerCounter(
            count = form.travelers,
            onIncrement = { viewModel.onTravelersChange(form.travelers + 1) },
            onDecrement = { viewModel.onTravelersChange(form.travelers - 1) }
        )

        if (destination != null) {
            val total = destination.price * form.travelers
            Card {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total Price:", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "$$total",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.submitBooking() },
            enabled = viewModel.isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirm Booking")
        }

        if (!viewModel.isFormValid) {
            Text(
                text = "Please fill in all fields correctly to continue.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}