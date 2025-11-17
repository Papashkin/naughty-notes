package com.antsfamily.naughtynotes.design.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.allnotes.AllNotesScreen
import com.antsfamily.naughtynotes.presentation.changepincode.ChangeExistingPinCodeScreen
import com.antsfamily.naughtynotes.presentation.home.HomeScreen
import com.antsfamily.naughtynotes.presentation.noteform.NoteFormScreen
import com.antsfamily.naughtynotes.presentation.settings.SettingsScreen
import com.antsfamily.naughtynotes.presentation.splash.SplashScreen
import com.antsfamily.naughtynotes.presentation.stats.StatsScreen
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.presentation.verifypincode.PinCodeVerificationScreen
import kotlinx.coroutines.launch

@Composable
fun Navigator() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { values ->
            NavHost(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(values),
                navController = navController,
                startDestination = Splash
            ) {
                composable<Splash> { _ ->
                    SplashScreen(
                        navigateToHome = {
                            navController.navigate(Home) { popUpToTop(navController) }
                        },
                        navigateToCheckPin = {
                            navController.navigate(PinCodeVerification) { popUpToTop(navController) }
                        },
                        showLockSnackbar = {
                            scope.launch {
                                snackbarHostState
                                    .showSnackbar(
                                        message = context.getString(
                                            R.string.splash_screen_too_many_incorrect_attempts,
                                            it
                                        ),
                                        duration = SnackbarDuration.Short
                                    )
                            }
                        },
                        showErrorSnackbar = {
                            scope.launch {
                                snackbarHostState
                                    .showSnackbar(
                                        message = context.getString(it.toStringId()),
                                        duration = SnackbarDuration.Short
                                    )
                            }
                        }
                    )
                }
                composable<PinCodeVerification>(
                    enterTransition = { slideUpAnimation() },
                    exitTransition = { slideDownAnimation() }
                ) { _ ->
                    BackHandler(true) {
                        //no-op
                    }
                    PinCodeVerificationScreen(
                        navigateToHome = {
                            navController.navigate(Home) { popUpToTop(navController) }
                        },
                    )
                }
                composable<Home> { _ ->
                    BackHandler(true) {
                        //no-op
                    }
                    HomeScreen(
                        navigateToNoteForm = { navController.navigate(NoteForm(it, null)) },
                        navigateToAllNotes = { navController.navigate(AllNotes(it)) },
                        navigateToSettings = { navController.navigate(Settings) }
                    )
                }
                composable<NoteForm>(
                    enterTransition = { slideInAnimation() },
                    exitTransition = { slideOutAnimation() }
                ) { entry ->
                    val data = entry.toRoute<NoteForm>()
                    BackHandler(true) {
                        //no-op
                    }
                    NoteFormScreen(
                        dateEpoch = data.dateEpoch,
                        noteId = data.noteId,
                        onNavigateBack = { navController.popBackStack() },
                        onErrorSnackbarShow = {
                            scope.launch {
                                snackbarHostState
                                    .showSnackbar(
                                        message = context.getString(it.toStringId()),
                                        duration = SnackbarDuration.Short
                                    )
                            }
                        }
                    )
                }
                composable<AllNotes>(
                    enterTransition = { slideInAnimation() },
                    popExitTransition = { slideOutAnimation() }
                ) { entry ->
                    val data = entry.toRoute<AllNotes>()
                    AllNotesScreen(
                        snackbarHostState = snackbarHostState,
                        epoch = data.dateEpoch,
                        navigateBack = { navController.popBackStack() },
                        navigateToNoteForm = {
                            navController.navigate(NoteForm(data.dateEpoch, it))
                        }
                    )
                }
                composable<Settings>(
                    enterTransition = { slideInAnimation() },
                    popExitTransition = { slideOutAnimation() }
                ) {
                    SettingsScreen(
                        onNavigateBack = { navController.popBackStack() },
                        onStatisticsNavigate = {navController.navigate(Stats)},
                        onChangePinNavigate = { navController.navigate(ChangeExistingPinCode) }
                    )
                }
                composable<ChangeExistingPinCode>(
                    enterTransition = { slideUpAnimation() },
                    exitTransition = { slideDownAnimation() }
                ) {
                    ChangeExistingPinCodeScreen(
                        navigateBack = { navController.popBackStack() },
                    )
                }
                composable<Stats>(
                    enterTransition = { slideInAnimation() },
                    exitTransition = { slideOutAnimation() }
                ) { _ ->
                    StatsScreen(
                        onNavigateBack = { navController.popBackStack() },
                    )
                }
            }
        }
    )
}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive = true
    }
}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.slideUpAnimation() =
    slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Up,
        animationSpec = tween(DURATION_ANIMATION)
    )

private fun AnimatedContentTransitionScope<NavBackStackEntry>.slideDownAnimation() =
    slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Down,
        animationSpec = tween(DURATION_ANIMATION)
    )

private fun AnimatedContentTransitionScope<NavBackStackEntry>.slideInAnimation() =
    slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Start,
        animationSpec = tween(DURATION_ANIMATION)
    )

private fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutAnimation() =
    slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.End,
        animationSpec = tween(DURATION_ANIMATION)
    )


const val DURATION_ANIMATION = 400