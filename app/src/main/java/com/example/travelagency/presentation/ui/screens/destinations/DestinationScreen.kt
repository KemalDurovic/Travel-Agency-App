package com.example.travelagency.presentation.ui.screens.destinations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.travelagency.model.destinationCategories
import com.example.travelagency.model.sampleDestinations
import com.example.travelagency.presentation.ui.components.CategoryChip
import com.example.travelagency.presentation.ui.components.DestinationCard
import com.example.travelagency.presentation.ui.components.EmptyState
import com.example.travelagency.presentation.ui.components.categoryEmoji
import com.example.travelagency.presentation.viewmodel.DestinationsViewModel

@Composable
fun DestinationsScreen(
    viewModel: DestinationsViewModel,
    onDestinationClick: (Int, String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = viewModel::onSearchQueryChange,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            placeholder = { Text("Search destinations...") },
            leadingIcon = { Icon(Icons.Default.Search, null, tint = Color(0xFF1565C0)) },
            singleLine = true,
            shape = RoundedCornerShape(50.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1565C0),
                unfocusedBorderColor = Color(0xFFBBDEFB)
            )
        )

        // LazyRow #3 — category filter chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            items(destinationCategories) { category ->
                CategoryChip(
                    label = category,
                    isSelected = uiState.selectedCategory == category,
                    onSelect = { viewModel.onCategorySelected(category) }
                )
            }
        }

        Text(
            text = viewModel.resultCountLabel,
            color = Color.Gray,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        // LazyColumn #1 — destinations list with empty state
        if (uiState.filteredDestinations.isEmpty()) {
            EmptyState(
                title = "No destinations found",
                subtitle = "Try a different category or search term",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.filteredDestinations, key = { it.id }) { dest ->
                    DestinationCard(
                        destination = dest,
                        onClick = { onDestinationClick(dest.id, dest.name) }
                    )
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationDetailScreen(
    destinationId: Int,
    destinationName: String,
    onBookClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    val destination = sampleDestinations.find { it.id == destinationId }
    var travelers by remember { mutableIntStateOf(1) }

    if (destination == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Destination not found")
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(destinationName, maxLines = 1) },
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
        },
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Total", color = Color.Gray, fontSize = 12.sp)
                        Text(
                            text = "$${destination.price * travelers}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color(0xFF1565C0)
                        )
                    }
                    Button(
                        onClick = { onBookClick(destination.id) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
                    ) {
                        Text("Book Now ✈️", fontSize = 16.sp)
                    }
                }
            }
        }
    ) { padding ->
        // LazyColumn #2 — detail content
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().height(220.dp).background(
                        Brush.linearGradient(colors = listOf(Color(0xFF0D47A1), Color(0xFF42A5F5)))
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = categoryEmoji(destination.category), fontSize = 60.sp)
                        Text(destination.country, color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(destination.name, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("⭐ ${destination.rating}", fontWeight = FontWeight.SemiBold)
                        Text("•", color = Color.Gray)
                        Text("🗓️ ${destination.duration} days", color = Color.Gray)
                        Text("•", color = Color.Gray)
                        Text("📍 ${destination.country}", color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("About", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(destination.description, color = Color(0xFF555555), lineHeight = 22.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("What's Included", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            items(destination.includes) { item ->
                Text("✅ $item", fontSize = 14.sp, modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp))
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Travelers", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { if (travelers > 1) travelers-- }, enabled = travelers > 1) {
                            Text("−", fontSize = 20.sp, color = Color(0xFF1565C0))
                        }
                        Text("$travelers", fontWeight = FontWeight.Bold, fontSize = 18.sp,
                            modifier = Modifier.width(32.dp), textAlign = TextAlign.Center)
                        IconButton(onClick = { if (travelers < 10) travelers++ }, enabled = travelers < 10) {
                            Text("+", fontSize = 20.sp, color = Color(0xFF1565C0))
                        }
                    }
                }
            }
        }
    }
}