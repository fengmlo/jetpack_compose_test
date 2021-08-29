package me.fengmlo.composetest

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NamedNavArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import me.fengmlo.composetest.ui.home.MainPage
import me.fengmlo.composetest.ui.text.TextPage

object MainDestinations {
    const val HOME = "home"
    const val TEXT_ROUTE = "text"
    const val COURSES_ROUTE = "courses"
    const val COURSE_DETAIL_ROUTE = "course"
    const val COURSE_DETAIL_ID_KEY = "courseId"
}

@ExperimentalAnimationApi
@Composable
fun NavGraph(navController: NavHostController) {
    AnimatedNavHost(navController = navController, startDestination = MainDestinations.HOME) {
        appComposable(route = MainDestinations.HOME) {
            MainPage(navController = navController)
        }
        appComposable(route = MainDestinations.TEXT_ROUTE) {
            TextPage(navController = navController)
        }
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.appComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> EnterTransition?)? = { _, _ ->
        slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300))
    },
    exitTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> ExitTransition?)? = { _, _ ->
        slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300))
    },
    popEnterTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> EnterTransition?)? = { _, _ ->
        slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300))
    },
    popExitTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> ExitTransition?)? = { _, _ ->
        slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300))
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