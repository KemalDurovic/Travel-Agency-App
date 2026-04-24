package com.example.travelagency.presentation.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelagency.presentation.ui.components.CityChip
import com.example.travelagency.presentation.ui.components.DestinationCard
import com.example.travelagency.presentation.ui.components.DestinationCardCompact
import com.example.travelagency.presentation.ui.components.EmptyState
import com.example.travelagency.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onDestinationClick: (Int, String) -> Unit,
    onSeeAllClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Hero Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFF1565C0), Color(0xFF42A5F5))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("✈️ Explore The World", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Your dream destination awaits", color = Color.White.copy(alpha = 0.85f), fontSize = 14.sp)
                }
            }
        }

        // Search bar
        item {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                placeholder = { Text("Search destinations...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF1565C0))
                },
                shape = RoundedCornerShape(50.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1565C0),
                    unfocusedBorderColor = Color(0xFFBBDEFB)
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Popular Cities — LazyRow #1
        item {
            Text(
                text = "Popular Destinations",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.popularCities) { city ->
                    CityChip(city = city)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Featured Packages header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Featured Packages", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D47A1))
                TextButton(onClick = onSeeAllClick) {
                    Text("See All", color = Color(0xFF1565C0))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        // Featured Destinations — LazyRow #2
        item {
            if (uiState.featuredDestinations.isEmpty()) {
                EmptyState(
                    title = "No featured destinations",
                    subtitle = "Check back soon!"
                )
            } else {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.featuredDestinations, key = { it.id }) { dest ->
                        DestinationCardCompact(
                            destination = dest,
                            onClick = { onDestinationClick(dest.id, dest.name) }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Top Rated section — LazyColumn items
        item {
            Text(
                text = "Top Rated ⭐",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        val topRated = uiState.featuredDestinations.filter { it.rating >= 4.8f }
        if (topRated.isEmpty()) {
            item {
                EmptyState(
                    emoji = "⭐",
                    title = "No top rated destinations yet",
                    subtitle = "Explore all our packages"
                )
            }
        } else {
            items(topRated, key = { "home_${it.id}" }) { dest ->
                DestinationCard(
                    destination = dest,
                    onClick = { onDestinationClick(dest.id, dest.name) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}