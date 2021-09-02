package me.fengmlo.composetest

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NamedNavArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import me.fengmlo.composetest.ui.button.ButtonPage
import me.fengmlo.composetest.ui.dialog.DialogPage
import me.fengmlo.composetest.ui.home.MainPage
import me.fengmlo.composetest.ui.text.TextPage
import me.fengmlo.composetest.ui.textfield.TextFieldPage

object MainDestinations {
    const val HOME = "home"
    const val TEXT_ROUTE = "text"
    const val TEXT_FIELD_ROUTE = "textField"
    const val BUTTON_ROUTE = "button"
    const val DIALOG = "dialog"
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun NavGraph(navController: NavHostController, dark: MutableState<Boolean>) {
    AnimatedNavHost(
        navController = navController,
        startDestination = MainDestinations.HOME,
        enterTransition = { _, _ ->
            slideInHorizontally(initialOffsetX = { it/*1000*/ }, animationSpec = tween(300))
        },
        exitTransition = { _, _ ->
            slideOutHorizontally(targetOffsetX = { -it /*-1000*/ }, animationSpec = tween(300))
        },
        popEnterTransition = { _, _ ->
            slideInHorizontally(initialOffsetX = { -it/*-1000*/ }, animationSpec = tween(300))
        },
        popExitTransition = { _, _ ->
            slideOutHorizontally(targetOffsetX = { it/*1000*/ }, animationSpec = tween(300))
        }
    ) {
        composable(route = MainDestinations.HOME) {
            MainPage(navController = navController, dark = dark)
        }
        composable(route = MainDestinations.TEXT_ROUTE) {
            TextPage(navController = navController)
        }
        composable(route = MainDestinations.TEXT_FIELD_ROUTE) {
            TextFieldPage(navController = navController)
        }
        composable(route = MainDestinations.BUTTON_ROUTE) {
            ButtonPage(naviController = navController)
        }
        composable(route = MainDestinations.DIALOG) {
            DialogPage(naviController = navController)
        }
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.appComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> EnterTransition?)? = { _, _ ->
        slideInHorizontally(initialOffsetX = { it/*1000*/ }, animationSpec = tween(300))
    },
    exitTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> ExitTransition?)? = { _, _ ->
        slideOutHorizontally(targetOffsetX = { -it /*-1000*/ }, animationSpec = tween(300))
    },
    popEnterTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> EnterTransition?)? = { _, _ ->
        slideInHorizontally(initialOffsetX = { -it/*-1000*/ }, animationSpec = tween(300))
    },
    popExitTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> ExitTransition?)? = { _, _ ->
        slideOutHorizontally(targetOffsetX = { it/*1000*/ }, animationSpec = tween(300))
    },
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) = composable(
    route,
    arguments,
    deepLinks,
    enterTransition,
    exitTransition,
    popEnterTransition,
    popExitTransition,
    content
)