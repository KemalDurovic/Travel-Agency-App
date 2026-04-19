package com.example.travelagency.presentation.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.travelagency.presentation.ui.screens.profile.components.ProfileAvatar
import com.example.travelagency.presentation.ui.screens.profile.components.ProfileInfoRow

@Composable
fun ProfileScreen() {
    // Simple hardcoded profile for now (MVP)
    val name = "Kemal Durovic"
    val email = "kemal.durovic@stu.ibu.edu.ba"
    val memberSince = "2026"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(24.dp))

        _root_ide_package_.com.example.travelagency.presentation.ui.screens.profile.components.ProfileAvatar(
            name = name
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = email,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Account Info",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                _root_ide_package_.com.example.travelagency.presentation.ui.screens.profile.components.ProfileInfoRow(
                    label = "Full Name",
                    value = name
                )
                _root_ide_package_.com.example.travelagency.presentation.ui.screens.profile.components.ProfileInfoRow(
                    label = "Email",
                    value = email
                )
                _root_ide_package_.com.example.travelagency.presentation.ui.screens.profile.components.ProfileInfoRow(
                    label = "Member Since",
                    value = memberSince
                )
                _root_ide_package_.com.example.travelagency.presentation.ui.screens.profile.components.ProfileInfoRow(
                    label = "Trips Booked",
                    value = "3"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Travel Stats",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    _root_ide_package_.com.example.travelagency.presentation.ui.screens.profile.StatCard(
                        value = "3",
                        label = "Countries"
                    )
                    _root_ide_package_.com.example.travelagency.presentation.ui.screens.profile.StatCard(
                        value = "14",
                        label = "Days Traveled"
                    )
                    _root_ide_package_.com.example.travelagency.presentation.ui.screens.profile.StatCard(
                        value = "2",
                        label = "Upcoming"
                    )
                }
            }
        }
    }
}

@Composable
fun StatCard(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
        )
    }
}