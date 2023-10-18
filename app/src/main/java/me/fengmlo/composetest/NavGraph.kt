@file:OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)

package me.fengmlo.composetest

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.fengmlo.composetest.ui.button.ButtonPage
import me.fengmlo.composetest.ui.constraintlayout.ConstraintLayoutPage
import me.fengmlo.composetest.ui.dialog.DialogPage
import me.fengmlo.composetest.ui.flow.FlowPage
import me.fengmlo.composetest.ui.home.MainPage
import me.fengmlo.composetest.ui.image.ImagePage
import me.fengmlo.composetest.ui.pager.HorizontalPagerPage
import me.fengmlo.composetest.ui.pager.PagerPage
import me.fengmlo.composetest.ui.text.TextPage
import me.fengmlo.composetest.ui.textfield.TextFieldPage

object MainDestinations {
    const val HOME = "home"
    const val TEXT_ROUTE = "text"
    const val TEXT_FIELD_ROUTE = "textField"
    const val BUTTON_ROUTE = "button"
    const val DIALOG = "dialog"
    const val IMAGE = "image"
    const val PAGER = "pager"
    const val HORIZONTAL_PAGER = "horizontalPager"
    const val FLOW = "flow"
    const val CONSTRAINT_LAYOUT = "constraint_layout"
}


val pages = listOf(
    Page(
        title = "Text",
        route = MainDestinations.TEXT_ROUTE,
        builder = { navController, _, _ -> TextPage(navController = navController) },
    ),
    Page(
        title = "TextField",
        route = MainDestinations.TEXT_FIELD_ROUTE,
        builder = { navController, _, _ -> TextFieldPage(navController = navController) },
    ),
    Page(
        title = "Button",
        route = MainDestinations.BUTTON_ROUTE,
        builder = { navController, _, _ -> ButtonPage(naviController = navController) },
    ),
    Page(
        title = "Dialog",
        route = MainDestinations.DIALOG,
        builder = { navController, _, _ -> DialogPage(naviController = navController) },
    ),
    Page(
        title = "Image",
        route = MainDestinations.IMAGE,
        builder = { navController, _, _ -> ImagePage(navController = navController) },
    ),
    Page(
        title = "Pager",
        route = MainDestinations.PAGER,
        builder = { navController, _, _ -> PagerPage(navController = navController) },
    ),
    Page(
        title = "HorizontalPager",
        route = MainDestinations.HORIZONTAL_PAGER,
        builder = { navController, _, _ -> HorizontalPagerPage(navController = navController) },
    ),
    Page(
        title = "Flow",
        route = MainDestinations.FLOW,
        builder = { navController, _, _ -> FlowPage(navController = navController) },
    ),
    Page(
        title = "ConstraintLayout",
        route = MainDestinations.CONSTRAINT_LAYOUT,
        builder = { navController, _, _ -> ConstraintLayoutPage(navController = navController) },
    )
)


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
        repeat(pages.size) {
            val page = pages[it]
            composable(route = page.route) {
                page.builder(this, navController, null, it)
            }
        }
//        composable(route = MainDestinations.TEXT_ROUTE) {
//            TextPage(navController = navController)
//        }
//        composable(route = MainDestinations.TEXT_FIELD_ROUTE) {
//            TextFieldPage(navController = navController)
//        }
//        composable(route = MainDestinations.BUTTON_ROUTE) {
//            ButtonPage(naviController = navController)
//        }
//        composable(route = MainDestinations.DIALOG) {
//            DialogPage(naviController = navController)
//        }
//        composable(route = MainDestinations.IMAGE) {
//            ImagePage(navController = navController)
//        }
//        composable(route = MainDestinations.PAGER) {
//            PagerPage(navController = navController)
//        }
//        composable(route = MainDestinations.HORIZONTAL_PAGER) {
//            HorizontalPagerPage(navController = navController)
//        }
//        composable(route = MainDestinations.FLOW) {
//            FlowPage(navController = navController)
//        }
//        composable(route = MainDestinations.CONSTRAINT_LAYOUT) {
//            ConstraintLayoutPage(navController = navController)
//        }
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

data class Page(
    val route: String,
    val title: String,
    val arguments: List<Any?>? = null,
    val builder: @Composable AnimatedContentScope.(NavHostController, List<Any?>?, NavBackStackEntry) -> Unit
)