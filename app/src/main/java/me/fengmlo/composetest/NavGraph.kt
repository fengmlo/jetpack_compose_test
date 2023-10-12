package me.fengmlo.composetest

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import me.fengmlo.composetest.ui.button.ButtonPage
import me.fengmlo.composetest.ui.dialog.DialogPage
import me.fengmlo.composetest.ui.home.MainPage
import me.fengmlo.composetest.ui.image.ImagePage
import me.fengmlo.composetest.ui.text.TextPage
import me.fengmlo.composetest.ui.textfield.TextFieldPage

object MainDestinations {
    const val HOME = "home"
    const val TEXT_ROUTE = "text"
    const val TEXT_FIELD_ROUTE = "textField"
    const val BUTTON_ROUTE = "button"
    const val DIALOG = "dialog"
    const val IMAGE = "image"
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun NavGraph(navController: NavHostController, dark: MutableState<Boolean>) {
    NavHost(
        navController = navController, startDestination = MainDestinations.HOME,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it/*1000*/ }, animationSpec = tween(300))
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -it /*-1000*/ }, animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it/*-1000*/ }, animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { it/*1000*/ }, animationSpec = tween(300))
        },
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
        composable(route = MainDestinations.IMAGE) {
            ImagePage(navController = navController)
        }
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.appComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        slideInHorizontally(initialOffsetX = { it/*1000*/ }, animationSpec = tween(300))
    },
    exitTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        slideOutHorizontally(targetOffsetX = { -it /*-1000*/ }, animationSpec = tween(300))
    },
    popEnterTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        slideInHorizontally(initialOffsetX = { -it/*-1000*/ }, animationSpec = tween(300))
    },
    popExitTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        slideOutHorizontally(targetOffsetX = { it/*1000*/ }, animationSpec = tween(300))
    },
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
) = composable(
    route, arguments, deepLinks, enterTransition, exitTransition, popEnterTransition, popExitTransition, content
)