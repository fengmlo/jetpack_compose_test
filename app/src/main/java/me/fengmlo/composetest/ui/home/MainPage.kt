package me.fengmlo.composetest.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import me.fengmlo.composetest.MainDestinations
import me.fengmlo.composetest.ui.theme.ComposeTestTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(navController: NavController, dark: MutableState<Boolean>) {

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    // A surface container using the 'background' color from the theme
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer(dark)
        },
        gesturesEnabled = true,
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    //                contentPadding = rememberInsetsPaddingValues(
                    //                    LocalWindowInsets.current.statusBars,
                    //                    additionalStart = 4.dp,
                    //                    additionalEnd = 4.dp,
                    //                    applyBottom = false,
                    //                ),
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "菜单")
                        }
                    },
                    title = {
                        Text(text = "Compose Test")
                    },
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .verticalScroll(state = rememberScrollState())
            ) {
                ListItem(title = "Text", onClick = {
                    navController.navigate(MainDestinations.TEXT_ROUTE)
                })

                ListItem(title = "TextField", onClick = {
                    navController.navigate(MainDestinations.TEXT_FIELD_ROUTE)
                })

                ListItem(title = "Button", onClick = {
                    navController.navigate(MainDestinations.BUTTON_ROUTE)
                })

                ListItem(title = "Dialog", onClick = {
                    navController.navigate(MainDestinations.DIALOG)
                })

                ListItem(title = "Image", onClick = {
                    navController.navigate(MainDestinations.IMAGE)
                })
            }
        }
    }
}

@Composable
private fun ListItem(title: String, onClick: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(start = 16.dp, end = 16.dp),
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            )
            Icon(
                modifier = Modifier.align(Alignment.CenterVertically),
                imageVector = Icons.Filled.NavigateNext,
                contentDescription = null
            )
        }
        Divider()
    }
}

@Composable
private fun Drawer(dark: MutableState<Boolean>) {
    ModalDrawerSheet(
        windowInsets = WindowInsets(top = 0),
    ) {
        Column {
            Box(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.onSurface)
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(start = 16.dp, end = 16.dp)
                    .height(56.dp)
//                    .width(DrawerDefaults.MaximumDrawerWidth),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.BottomStart),
                    text = "Drawer",
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.surface
                )
            }
            Button(
                onClick = { dark.component2().invoke(!dark.value) }, modifier = Modifier.wrapContentSize()
            ) {
                Text("切换主题")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDrawer() {
    ComposeTestTheme {
        val dark = remember { mutableStateOf(false) }
        Drawer(dark = dark)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainPage() {
    MainPage(rememberNavController(), remember { mutableStateOf(false) })
}