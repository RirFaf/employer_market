package android.employer_market.ui.navigation

import android.employer_market.network.models.ResumeModel
import android.employer_market.ui.navigation.extensions.sharedViewModel
import android.employer_market.ui.screens.messenger.ChatListScreen
import android.employer_market.ui.screens.FavouritesScreen
import android.employer_market.ui.screens.messenger.MessengerScreen
import android.employer_market.ui.screens.profile.ProfileScreen
import android.employer_market.ui.screens.ResponsesListScreen
import android.employer_market.ui.screens.SearchScreen
import android.employer_market.ui.screens.SelectedResumeScreen
import android.employer_market.ui.screens.profile.ProfileRedactorScreen
import android.employer_market.ui.screens.vacancy.ResumeRedactorScreen
import android.employer_market.ui.screens.vacancy.VacancyScreen
import android.employer_market.view_model.FavouritesViewModel
import android.employer_market.view_model.MessengerViewModel
import android.employer_market.view_model.ProfileViewModel
import android.employer_market.view_model.ResponsesViewModel
import android.employer_market.view_model.ResumeViewModel
import android.employer_market.view_model.SearchViewModel
import android.util.Log
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute


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
        startDestination = Screen.SearchScreen
    ) {
        composable<Screen.SearchScreen>(
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
        composable<Screen.SelectedResumeScreen>(
            enterTransition = { customEnterTransition },
            exitTransition = { customExitTransition },
            popEnterTransition = { customEnterTransition },
            popExitTransition = { customExitTransition },
        ) { entry ->
            val args = entry.toRoute<Screen.SelectedResumeScreen>()

            SelectedResumeScreen(
                navController = navController,
                resume = ResumeModel(
                    id = args.id,
                    studentId = args.studentId,
                    salary = args.salary,
                    keySkills = args.keySkills,
                    secondName = args.secondName,
                    firstName = args.firstName,
                    patronymicName = args.patronymicName,
                    birthDate = args.birthDate,
                    university = args.university,
                    institute = args.institute,
                    course = args.course,
                    aboutMe = args.aboutMe,
                    gender = args.gender,
                    city = args.city,
                    direction = args.direction,
                    liked = args.liked,
                )
            )
        }

        composable<Screen.FavouritesScreen>(
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
        composable<Screen.ChatListScreen>(
            enterTransition = { customEnterTransition },
            exitTransition = { customExitTransition },
            popEnterTransition = { customEnterTransition },
            popExitTransition = { customExitTransition },
        ) {
            ChatListScreen(navController = navController)
        }

        composable<Screen.MessengerScreen>(
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

        composable<Screen.ResponsesListScreen>(
            enterTransition = { customEnterTransition },
            exitTransition = { customExitTransition },
            popEnterTransition = { customEnterTransition },
            popExitTransition = { customExitTransition },
        ) {
            val responsesViewModel =
                viewModel<ResponsesViewModel>(factory = ResponsesViewModel.Factory)
            val state by responsesViewModel.uiState.collectAsStateWithLifecycle()
            ResponsesListScreen(
                navController = navController,
                state = state,
                onEvent = responsesViewModel::onEvent
            )
        }

        navigation<Screen.Profile>(startDestination = Screen.ProfileScreen) {
            composable<Screen.ProfileScreen>(
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
                ProfileScreen(
                    navController = navController,
                    state = state,
                    onEvent = profileViewModel::onEvent
                )
            }

            composable<Screen.ProfileRedactorScreen>(
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
                ProfileRedactorScreen(
                    navController = navController,
                    state = state,
                    onEvent = profileViewModel::onEvent
                )
            }
        }

        composable<Screen.VacancyScreen>(
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
            VacancyScreen(
                navController = navController,
                state = state,
                onEvent = resumeViewModel::onEvent
            )
        }
        composable<Screen.VacancyScreen>(
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

