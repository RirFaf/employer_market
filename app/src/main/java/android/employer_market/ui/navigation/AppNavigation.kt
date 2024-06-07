package android.employer_market.ui.navigation

import android.employer_market.network.models.VacancyModel
import android.employer_market.ui.navigation.extensions.sharedViewModel
import android.employer_market.ui.screens.messenger.ChatListScreen
import android.employer_market.ui.screens.FavouritesScreen
import android.employer_market.ui.screens.messenger.MessengerScreen
import android.employer_market.ui.screens.profile.ProfileScreen
import android.employer_market.ui.screens.ResponsesListScreen
import android.employer_market.ui.screens.SearchScreen
import android.employer_market.ui.screens.VacancyScreen
import android.employer_market.ui.screens.resume.ResumeRedactorScreen
import android.employer_market.ui.screens.resume.ResumeScreen
import android.employer_market.view_model.FavouritesViewModel
import android.employer_market.view_model.MessengerViewModel
import android.employer_market.view_model.ProfileViewModel
import android.employer_market.view_model.ResponsesViewModel
import android.employer_market.view_model.ResumeViewModel
import android.employer_market.view_model.SearchViewModel
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph(navController: NavHostController) {
    val customEnterTransition: EnterTransition =
        fadeIn(
            animationSpec = tween(150, easing = LinearEasing)
        )
    val customExitTransition: ExitTransition =
        fadeOut(
            animationSpec = tween(150, easing = LinearEasing)
        )

    NavHost(
        navController = navController,
        startDestination = Screen.SearchScreen.route
    ) {
        composable(
            route = Screen.SearchScreen.route,
            enterTransition = { customEnterTransition },
            exitTransition = { customExitTransition },
            popEnterTransition = { customEnterTransition },
            popExitTransition = { customExitTransition },
        ) {
            val searchViewModel = viewModel<SearchViewModel>(
                factory = SearchViewModel.Factory
            )
            val state by searchViewModel.uiState.collectAsStateWithLifecycle()
            SearchScreen(
                navController = navController,
                onEvent = searchViewModel::onEvent,
                state = state
            )
        }
        composable(
            route = Screen.VacancyScreen.route +
                    "/{id}" +
                    "/{position}" +
                    "/{salary}" +
                    "/{companyName}" +
                    "/{edArea}" +
                    "/{formOfEmployment}" +
                    "/{requirements}" +
                    "/{location}" +
                    "/{about}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                },
                navArgument(name = "position") {
                    type = NavType.StringType
                },
                navArgument(name = "salary") {
                    type = NavType.IntType
                },
                navArgument(name = "companyName") {
                    type = NavType.StringType
                },
                navArgument(name = "edArea") {
                    type = NavType.StringType
                },
                navArgument(name = "formOfEmployment") {
                    type = NavType.StringType
                },
                navArgument(name = "requirements") {
                    type = NavType.StringType
                },
                navArgument(name = "location") {
                    type = NavType.StringType
                },
                navArgument(name = "about") {
                    type = NavType.StringType
                },
            ),
            enterTransition = { customEnterTransition },
            exitTransition = { customExitTransition },
            popEnterTransition = { customEnterTransition },
            popExitTransition = { customExitTransition },
        ) {
            VacancyScreen(
                navController = navController,
                vacancy = VacancyModel(
                    id = it.arguments?.getInt("id")!!,
                    position = it.arguments?.getString("position")!!,
                    salary = it.arguments?.getInt("salary")!!,
                    companyName = it.arguments?.getString("companyName")!!,
                    edArea = it.arguments?.getString("edArea")!!,
                    formOfEmployment = it.arguments?.getString("formOfEmployment")!!,
                    requirements = it.arguments?.getString("requirements")!!,
                    location = it.arguments?.getString("location")!!,
                    about = it.arguments?.getString("about")!!
                )
            )
        }

        composable(
            route = Screen.FavouritesScreen.route,
            enterTransition = { customEnterTransition },
            exitTransition = { customExitTransition },
            popEnterTransition = { customEnterTransition },
            popExitTransition = { customExitTransition },
        ) {
            val favouritesViewModel = viewModel<FavouritesViewModel>(
                factory = FavouritesViewModel.Factory
            )
            val state by favouritesViewModel.uiState.collectAsStateWithLifecycle()
            FavouritesScreen(
                navController = navController,
                onEvent = favouritesViewModel::onEvent,
                state = state
            )
        }
        composable(
            route = Screen.ChatListScreen.route,
            enterTransition = { customEnterTransition },
            exitTransition = { customExitTransition },
            popEnterTransition = { customEnterTransition },
            popExitTransition = { customExitTransition },
        ) {
            ChatListScreen(navController = navController)
        }

        composable(
            route = Screen.MessengerScreen.route,
            enterTransition = { customEnterTransition },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(0, easing = LinearEasing)
                )
            },
            popEnterTransition = { customEnterTransition },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(0, easing = LinearEasing)
                )
            },
        ) {
            val messengerViewModel = viewModel<MessengerViewModel>(
                factory = MessengerViewModel.Factory
            )
            val state by messengerViewModel.uiState.collectAsStateWithLifecycle()
            MessengerScreen(
                navController = navController,
                onEvent = messengerViewModel::onEvent,
                state = state
            )
        }

        composable(
            route = Screen.ResponsesListScreen.route,
            enterTransition = { customEnterTransition },
            exitTransition = { customExitTransition },
            popEnterTransition = { customEnterTransition },
            popExitTransition = { customExitTransition },
        ) {
            val responsesViewModel = viewModel<ResponsesViewModel>(factory = ResponsesViewModel.Factory)
            val state by responsesViewModel.uiState.collectAsStateWithLifecycle()
            ResponsesListScreen(
                navController = navController,
                state = state,
                onEvent = responsesViewModel::onEvent
            )
        }

        composable(
            route = Screen.ProfileScreen.route,
            enterTransition = { customEnterTransition },
            exitTransition = { customExitTransition },
            popEnterTransition = { customEnterTransition },
            popExitTransition = { customExitTransition },
        ) {entry ->
            val profileViewModel = entry.sharedViewModel<ProfileViewModel>(
                factory = ProfileViewModel.Factory,
                navController = navController
            )
            val state by profileViewModel.uiState.collectAsStateWithLifecycle()
            ProfileScreen(
                navController = navController,
                state = state,
                onEvent = profileViewModel::onEvent
            )
        }

        composable(
            route = Screen.ProfileRedactorScreen.route,
            enterTransition = { customEnterTransition },
            exitTransition = { customExitTransition },
            popEnterTransition = { customEnterTransition },
            popExitTransition = { customExitTransition },
        ) { entry ->
            val profileViewModel = entry.sharedViewModel<ProfileViewModel>(
                factory = ProfileViewModel.Factory,
                navController = navController
            )
            val state by profileViewModel.uiState.collectAsStateWithLifecycle()
        }

        composable(
            route = Screen.ResumeScreen.route,
            enterTransition = { customEnterTransition },
            exitTransition = { customExitTransition },
            popEnterTransition = { customEnterTransition },
            popExitTransition = { customExitTransition },
        ) { entry ->
            val resumeViewModel = entry.sharedViewModel<ResumeViewModel>(
                navController = navController,
                factory = ResumeViewModel.Factory
            )
            val state by resumeViewModel.uiState.collectAsStateWithLifecycle()
            ResumeScreen(
                navController = navController,
                state = state,
                onEvent = resumeViewModel::onEvent
            )
        }
        composable(
            route = Screen.ResumeRedactorScreen.route,
            enterTransition = { customEnterTransition },
            exitTransition = { customExitTransition },
            popEnterTransition = { customEnterTransition },
            popExitTransition = { customExitTransition },
        ) { entry ->
            val resumeViewModel = entry.sharedViewModel<ResumeViewModel>(
                navController = navController,
                factory = ResumeViewModel.Factory
            )
            val state by resumeViewModel.uiState.collectAsStateWithLifecycle()
            ResumeRedactorScreen(
                navController = navController,
                state = state,
                onEvent = resumeViewModel::onEvent
            )
        }
    }
}
