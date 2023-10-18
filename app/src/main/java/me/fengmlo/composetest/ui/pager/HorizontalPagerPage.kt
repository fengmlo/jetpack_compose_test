package me.fengmlo.composetest.ui.pager

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import me.fengmlo.composetest.ui.common.AppTopBar
import kotlin.math.absoluteValue

@ExperimentalFoundationApi
@Composable
fun HorizontalPagerPage(navController: NavHostController) {
    val pageCount = 10
    val pagerState = rememberPagerState {
        pageCount
    }
    val coroutineScope = rememberCoroutineScope()
    val fling = PagerDefaults.flingBehavior(state = pagerState, pagerSnapDistance = PagerSnapDistance.atMost(10))

    LaunchedEffect(pagerState) {
        snapshotFlow {
            pagerState.currentPage
        }.collect { page ->
            Log.d("Page change", "Page changed to $page")
        }
    }

    Scaffold(
        topBar = { AppTopBar(navController = navController, title = "HorizontalPager") },
    ) {
        Column(modifier = Modifier.padding(it)) {
            Box {
                HorizontalPager(
                    modifier = Modifier
//                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(200.dp),
                    state = pagerState,
                    contentPadding = PaddingValues(horizontal = 64.dp),
                    flingBehavior = fling,
                ) { page ->
                    Card(
                        modifier = Modifier
//                            .size(200.dp)
//                            .padding(16.dp)
                            .fillMaxSize()
                            .graphicsLayer {
                                // Calculate the absolute offset for the current page from the
                                // scroll position. We use the absolute value which allows us to mirror
                                // any effects for both directions
                                val pageOffset = (
                                        (pagerState.currentPage - page) + pagerState
                                            .currentPageOffsetFraction
                                        ).absoluteValue

                                // We animate the alpha, between 50% and 100%
                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                                scaleX = lerp(
                                    start = 0.8f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                                scaleY = scaleX
//                                rotationY = scaleX * 180
                            }
                    ) {
                        Text(text = "Page:$page")
                    }
                }

                Row(
                    Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pageCount) { iteration ->
                        val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(20.dp)

                        )
                    }
                }

            }

            Button(onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(5)
                }
            }) {
                Text(text = "Scroll to page 5")
            }
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun PreviewHorizontalPagerPage() {
    HorizontalPagerPage(navController = rememberNavController())
}