package me.fengmlo.composetest.ui.flow;

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import me.fengmlo.composetest.ui.common.AppTopBar

@ExperimentalLayoutApi
@Composable
fun FlowPage(navController: NavHostController) {

    val rows = 3
    val columns = 3

    Scaffold(
        topBar = { AppTopBar(navController = navController, title = "Flow") },
    )
    {
        Column(modifier = Modifier.padding(it)) {
            FlowRow(
                modifier = Modifier.padding(16.dp),
                maxItemsInEachRow = 3,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),

            ) {
                val itemModifier = Modifier
//                    .padding(4.dp)
                    .height(80.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary)
                repeat(rows * columns + 1) {
                    Spacer(modifier = itemModifier)
                }
            }
        }
    }
}

@ExperimentalLayoutApi
@Preview
@Composable
fun PreviewFlowPage() {
    FlowPage(navController = rememberNavController())
}