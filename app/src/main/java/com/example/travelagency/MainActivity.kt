package com.example.travelagency

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.travelagency.presentation.ui.screens.booking.BookingScreen
import com.example.travelagency.presentation.ui.screens.destinations.DestinationDetailScreen
import com.example.travelagency.presentation.ui.screens.destinations.DestinationsScreen
import com.example.travelagency.presentation.ui.screens.home.HomeScreen
import com.example.travelagency.presentation.ui.screens.profile.ProfileScreen
import com.example.travelagency.presentation.ui.screens.search.SearchScreen
import com.example.travelagency.presentation.theme.TravelAgencyTheme
import com.example.travelagency.presentation.viewmodel.BookingViewModel
import com.example.travelagency.presentation.viewmodel.DestinationsViewModel
import com.example.travelagency.presentation.viewmodel.HomeViewModel
import com.example.travelagency.presentation.viewmodel.SearchViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelAgencyTheme {
                TravelApp()
            }
        }
    }
}

@Composable
fun TravelApp() {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = viewModel()
    val destinationsViewModel: DestinationsViewModel = viewModel()
    val searchViewModel: SearchViewModel = viewModel()
    val bookingViewModel: BookingViewModel = viewModel()

    val bottomNavItems = listOf(
        BottomNavItem("home", "Home", Icons.Default.Home),
        BottomNavItem("destinations", "Trips", Icons.AutoMirrored.Filled.List),
        BottomNavItem("search", "Search", Icons.Default.Search),
        BottomNavItem("profile", "Profile", Icons.Default.Person)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in listOf("home", "destinations", "search", "profile")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo("home") { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") {
                HomeScreen(
                    viewModel = homeViewModel,
                    onDestinationClick = { id, name ->
                        navController.navigate("detail/$id/$name")
                    },
                    onSeeAllClick = {
                        navController.navigate("destinations")
                    }
                )
            }

            composable("destinations") {
                DestinationsScreen(
                    viewModel = destinationsViewModel,
                    onDestinationClick = { id, name ->
                        navController.navigate("detail/$id/$name")
                    }
                )
            }

            composable(
                route = "detail/{destinationId}/{destinationName}",
                arguments = listOf(
                    navArgument("destinationId") { type = NavType.IntType },
                    navArgument("destinationName") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("destinationId") ?: 1
                val name = backStackEntry.arguments?.getString("destinationName") ?: ""
                DestinationDetailScreen(
                    destinationId = id,
                    destinationName = name,
                    onBookClick = { destId ->
                        bookingViewModel.setDestination(destId)
                        navController.navigate("booking")
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            composable("booking") {
                BookingScreen(
                    viewModel = bookingViewModel,
                    onBack = { navController.popBackStack() },
                    onSuccess = {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                )
            }

            composable("search") {
                SearchScreen(
                    viewModel = searchViewModel,
                    onDestinationClick = { id, name ->
                        navController.navigate("detail/$id/$name")
                    }
                )
            }

            composable("profile") {
                ProfileScreen()
            }
        }
    }
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)