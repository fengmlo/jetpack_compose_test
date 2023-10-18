package me.fengmlo.composetest.ui.pager

import ListItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import me.fengmlo.composetest.MainDestinations
import me.fengmlo.composetest.ui.common.AppTopBar

@Composable
fun PagerPage(navController: NavHostController) {
    Scaffold(
        topBar = { AppTopBar(navController = navController, title = "Pager") },
    )
    {
        Column(modifier = Modifier.padding(it)) {
            ListItem(title = "HorizontalPager") {
                navController.navigate(MainDestinations.HORIZONTAL_PAGER)
            }
        }

    }
}
