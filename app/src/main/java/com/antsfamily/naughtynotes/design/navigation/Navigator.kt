package com.antsfamily.naughtynotes.design.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.allnotes.AllNotesScreen
import com.antsfamily.naughtynotes.presentation.home.HomeScreen
import com.antsfamily.naughtynotes.presentation.noteform.NoteFormScreen
import com.antsfamily.naughtynotes.presentation.settings.SettingsScreen
import com.antsfamily.naughtynotes.presentation.splash.SplashScreen
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
            print(values.toString())
            NavHost(
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
                        }
                    )
                }
                composable<PinCodeVerification> { _ ->
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
                composable<NoteForm> { entry ->
                    val data = entry.toRoute<NoteForm>()
                    BackHandler(true) {
                        //no-op
                    }
                    NoteFormScreen(
                        dateEpoch = data.dateEpoch,
                        noteId = data.noteId,
                        onNavigateBack = { navController.popBackStack() },
                        onSnackbarShow = {
                            scope.launch {
                                snackbarHostState
                                    .showSnackbar(message = it, duration = SnackbarDuration.Short)
                            }
                        }
                    )
                }
                composable<AllNotes> { entry ->
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
                composable<Settings> {
                    SettingsScreen(
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
