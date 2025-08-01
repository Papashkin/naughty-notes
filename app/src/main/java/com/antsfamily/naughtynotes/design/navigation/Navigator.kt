package com.antsfamily.naughtynotes.design.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.antsfamily.naughtynotes.presentation.allnotes.AllNotesScreen
import com.antsfamily.naughtynotes.presentation.noteform.NoteFormScreen
import com.antsfamily.naughtynotes.presentation.home.HomeScreen
import com.antsfamily.naughtynotes.presentation.splash.SplashScreen

@Composable
fun Navigator() {
    val navController = rememberNavController()
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
                        navigateToHome = { navController.navigate(Home) { popUpToTop(navController) } },
                    )
                }
                composable<Home> { _ ->
                    BackHandler(true) {
                        //no-op
                    }
                    HomeScreen(
                        navigateToNoteForm = { navController.navigate(NoteForm(it, null)) },
                        navigateToAllNotes = { navController.navigate(AllNotes(it)) }
                    )
                }
                composable<NoteForm> { entry ->
                    val data = entry.toRoute<NoteForm>()
                    BackHandler(true) {
                        //no-op
                    }
                    NoteFormScreen(
                        snackbarHostState = snackbarHostState,
                        dateEpoch = data.dateEpoch,
                        noteId = data.noteId,
                        onNavigateBack = { navController.popBackStack() }
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
            }
        }
    )
}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive = true
    }
}
