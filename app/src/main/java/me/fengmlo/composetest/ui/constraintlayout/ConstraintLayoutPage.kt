package me.fengmlo.composetest.ui.constraintlayout;

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import me.fengmlo.composetest.ui.common.AppTopBar

@Composable
fun ConstraintLayoutPage(navController: NavHostController) {
    Scaffold(
        topBar = { AppTopBar(navController = navController, title = "ConstraintLayout") },
    )
    {
        Column(modifier = Modifier.padding(it)) {

        }
    }
}

@Preview
@Composable
fun PreviewConstraintLayoutPager() {
    ConstraintLayoutPage(navController = rememberNavController())
}