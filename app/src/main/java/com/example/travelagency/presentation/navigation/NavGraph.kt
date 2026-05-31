package com.example.travelagency.presentation.navigation

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
import com.example.travelagency.ProfileScreen
import com.example.travelagency.presentation.ui.screens.search.SearchScreen
import com.example.travelagency.presentation.viewmodel.BookingViewModel
import com.example.travelagency.presentation.viewmodel.DestinationsViewModel
import com.example.travelagency.presentation.viewmodel.HomeViewModel
import com.example.travelagency.presentation.viewmodel.SearchViewModel

object Routes {
    const val HOME = "home"
    const val DESTINATIONS = "destinations"
    const val DETAIL = "detail/{destinationId}/{destinationName}"
    const val BOOKING = "booking"
    const val SEARCH = "search"
    const val PROFILE = "profile"

    fun detailRoute(id: Int, name: String) = "detail/$id/$name"
}

@Composable
fun TravelNavGraph() {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = viewModel()
    val destinationsViewModel: DestinationsViewModel = viewModel()
    val searchViewModel: SearchViewModel = viewModel()
    val bookingViewModel: BookingViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in listOf(
        Routes.HOME, Routes.DESTINATIONS, Routes.SEARCH, Routes.PROFILE
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    listOf(
                        Triple(Routes.HOME, "Home", Icons.Default.Home),
                        Triple(Routes.DESTINATIONS, "Trips", Icons.AutoMirrored.Filled.List),
                        Triple(Routes.SEARCH, "Search", Icons.Default.Search),
                        Triple(Routes.PROFILE, "Profile", Icons.Default.Person)
                    ).forEach { (route, label, icon) ->
                        NavigationBarItem(
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label) },
                            selected = currentRoute == route,
                            onClick = {
                                navController.navigate(route) {
                                    popUpTo(Routes.HOME) { saveState = true }
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
            startDestination = Routes.HOME,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    viewModel = homeViewModel,
                    onDestinationClick = { id, name ->
                        navController.navigate(Routes.detailRoute(id, name))
                    },
                    onSeeAllClick = { navController.navigate(Routes.DESTINATIONS) }
                )
            }

            composable(Routes.DESTINATIONS) {
                DestinationsScreen(
                    viewModel = destinationsViewModel,
                    onDestinationClick = { id, name ->
                        navController.navigate(Routes.detailRoute(id, name))
                    }
                )
            }

            composable(
                route = Routes.DETAIL,
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
                        navController.navigate(Routes.BOOKING)
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Routes.BOOKING) {
                BookingScreen(
                    viewModel = bookingViewModel,
                    onBack = { navController.popBackStack() },
                    onSuccess = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = true }
                        }
                    }
                )
            }

            composable(Routes.SEARCH) {
                SearchScreen(
                    viewModel = searchViewModel,
                    onDestinationClick = { id, name ->
                        navController.navigate(Routes.detailRoute(id, name))
                    }
                )
            }

            composable(Routes.PROFILE) {
                ProfileScreen()
            }
        }
    }
}