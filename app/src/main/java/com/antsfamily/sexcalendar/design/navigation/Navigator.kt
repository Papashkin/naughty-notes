package com.antsfamily.sexcalendar.design.navigation

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
import com.antsfamily.sexcalendar.presentation.allnotes.AllNotesScreen
import com.antsfamily.sexcalendar.presentation.createnote.CreateNoteScreen
import com.antsfamily.sexcalendar.presentation.home.HomeScreen
import com.antsfamily.sexcalendar.presentation.splash.SplashScreen

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
                        navigateToCreateNote = { navController.navigate(CreateNote(it)) },
                        navigateToAllNotes = {
                            navController.navigate(AllNotes(it))
                        }
                    )
                }
                composable<CreateNote> { entry ->
                    val data = entry.toRoute<CreateNote>()
                    BackHandler(true) {
                        //no-op
                    }
                    CreateNoteScreen(dateEpoch = data.dateEpoch) {
                        navController.popBackStack()
                    }
                }
                composable<AllNotes> { entry ->
                    val data = entry.toRoute<AllNotes>()
                    AllNotesScreen(
                        snackbarHostState = snackbarHostState,
                        epoch = data.dateEpoch,
                        navigateBack = { navController.popBackStack() },
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
