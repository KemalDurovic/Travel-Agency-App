package com.example.travelagency.presentation.ui.screens.booking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.travelagency.model.sampleDestinations
import com.example.travelagency.presentation.viewmodel.BookingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    viewModel: BookingViewModel,
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val form = uiState.form
    val destination = sampleDestinations.find { it.id == form.destinationId }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            viewModel.resetBooking()
            onSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Your Trip") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1565C0),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Destination summary card
            destination?.let { dest ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Booking for:", color = Color.Gray, fontSize = 12.sp)
                            Text(dest.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("📍 ${dest.country}", color = Color.Gray, fontSize = 13.sp)
                            Text(
                                "🗓️ ${dest.duration} days  •  $${dest.price} per person",
                                fontSize = 13.sp,
                                color = Color(0xFF1565C0)
                            )
                        }
                    }
                }
            }

            // Full name field
            item {
                OutlinedTextField(
                    value = form.fullName,
                    onValueChange = viewModel::onNameChange,
                    label = { Text("Full Name *") },
                    placeholder = { Text("Enter your full name") },
                    isError = uiState.nameError != null,
                    supportingText = {
                        if (uiState.nameError != null)
                            Text(uiState.nameError!!, color = MaterialTheme.colorScheme.error)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1565C0),
                        focusedLabelColor = Color(0xFF1565C0)
                    )
                )
            }

            // Email field
            item {
                OutlinedTextField(
                    value = form.email,
                    onValueChange = viewModel::onEmailChange,
                    label = { Text("Email Address *") },
                    placeholder = { Text("you@example.com") },
                    isError = uiState.emailError != null,
                    supportingText = {
                        if (uiState.emailError != null)
                            Text(uiState.emailError!!, color = MaterialTheme.colorScheme.error)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1565C0),
                        focusedLabelColor = Color(0xFF1565C0)
                    )
                )
            }

            // Traveler counter
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F9FF))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Travelers", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { viewModel.onTravelersChange(form.travelers - 1) },
                                enabled = form.travelers > 1
                            ) {
                                Icon(Icons.Default.KeyboardArrowDown, "Decrease", tint = Color(0xFF1565C0))
                            }
                            Text(
                                text = "${form.travelers}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.width(32.dp),
                                textAlign = TextAlign.Center
                            )
                            IconButton(
                                onClick = { viewModel.onTravelersChange(form.travelers + 1) },
                                enabled = form.travelers < 10
                            ) {
                                Icon(Icons.Default.Add, "Increase", tint = Color(0xFF1565C0))
                            }
                        }
                    }
                }
            }

            // Total price
            destination?.let { dest ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Total Price", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(
                                text = "$${dest.price * form.travelers}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color(0xFF1565C0)
                            )
                        }
                    }
                }
            }

            // Submit button
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = viewModel::submitBooking,
                    enabled = viewModel.isFormValid,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1565C0),
                        disabledContainerColor = Color(0xFFBBDEFB)
                    )
                ) {
                    Text(
                        "Confirm Booking ✈️",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                if (!viewModel.isFormValid) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "Please fill in all required fields correctly",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}