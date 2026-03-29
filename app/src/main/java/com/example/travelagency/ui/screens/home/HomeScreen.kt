package com.example.travelagency.ui.screens.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.travelagency.ui.screens.home.components.*
import com.example.travelagency.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onDestinationClick: (Int) -> Unit,
    onSeeAllClick: () -> Unit
) {
    val featuredDestinations by viewModel.featuredDestinations.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        WelcomeBanner()

        Spacer(modifier = Modifier.height(8.dp))

        QuickStatRow()

        Spacer(modifier = Modifier.height(16.dp))

        SectionHeader(title = "Featured Destinations", onSeeAll = onSeeAllClick)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            featuredDestinations.forEach { destination ->
                FeaturedDestinationCard(
                    destination = destination,
                    onClick = { onDestinationClick(destination.id) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}