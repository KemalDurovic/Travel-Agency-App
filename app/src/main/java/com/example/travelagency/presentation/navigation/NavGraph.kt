package com.example.travelagency.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.travelagency.presentation.ui.screens.booking.BookingScreen
import com.example.travelagency.presentation.ui.screens.destinations.DestinationDetailScreen
import com.example.travelagency.presentation.ui.screens.destinations.DestinationsScreen
import com.example.travelagency.presentation.ui.screens.home.HomeScreen
import com.example.travelagency.presentation.ui.screens.profile.ProfileScreen
import com.example.travelagency.presentation.ui.screens.search.SearchScreen
import com.example.travelagency.presentation.viewmodel.BookingViewModel
import com.example.travelagency.presentation.viewmodel.DestinationViewModel
import com.example.travelagency.presentation.viewmodel.HomeViewModel
import com.example.travelagency.presentation.viewmodel.SearchViewModel

// ── Route constants ───────────────────────────────────────────────────────────
object Routes {
    const val HOME = "home"
    const val DESTINATIONS = "destinations"
    const val DETAIL = "detail/{destinationId}/{destinationName}"
    const val BOOKING = "booking"
    const val SEARCH = "search"
    const val PROFILE = "profile"

    fun detailRoute(destinationId: Int, destinationName: String) =
        "detail/$destinationId/$destinationName"
}

// ── Main NavGraph ─────────────────────────────────────────────────────────────
@Composable
fun TravelNavGraph() {
    val navController = rememberNavController()

    // ViewModels are created here so they survive navigation
    val homeViewModel: HomeViewModel = viewModel()
    val destinationsViewModel: DestinationViewModel = viewModel()
    val searchViewModel: SearchViewModel = viewModel()
    val bookingViewModel: BookingViewModel = viewModel()

    MainScaffold(navController = navController) {
        NavHost(
            navController = navController,
            startDestination = Routes.HOME
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    viewModel = homeViewModel,
                    onDestinationClick = { id, name ->
                        navController.navigate(Routes.detailRoute(id, name))
                    },
                    onSeeAllClick = {
                        navController.navigate(Routes.DESTINATIONS)
                    }
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
                // Passing 2 arguments: id (Int) + name (String)
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