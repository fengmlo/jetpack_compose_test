package me.fengmlo.composetest.ui.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.twotone.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import me.fengmlo.composetest.ui.common.AppTopBar

@Composable
fun ButtonPage(naviController: NavHostController) {

    val snackbarHostState = remember { SnackbarHostState() }
    val channel = remember { Channel<String>(capacity = Channel.RENDEZVOUS) }
    LaunchedEffect(channel) {
        channel.receiveAsFlow().collect {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
        topBar = { AppTopBar(navController = naviController, title = "Button") }
    ) {
        Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
            Button(modifier = Modifier.padding(16.dp), onClick = { channel.trySend("点击Button") }) {
                Text(text = "按钮")
            }
            Divider()

            Button(
                modifier = Modifier.padding(16.dp),
                onClick = { channel.trySend("点击Button") },
                enabled = false,
            ) {
                Text(text = "按钮已禁用")
            }
            Divider()

            Button(
                modifier = Modifier.padding(16.dp),
                onClick = { channel.trySend("点击Button") },
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                ),
            ) {
                Text(text = "按钮更改默认Elevation")
            }
            Divider()

            Button(
                modifier = Modifier.padding(16.dp),
                onClick = { channel.trySend("点击Button") },
                shape = CircleShape,
                border = BorderStroke(1.dp, color = Color.Black),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black,
                    disabledBackgroundColor = Color.Gray,
                    disabledContentColor = Color.Gray
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Icon(imageVector = Icons.TwoTone.FavoriteBorder, contentDescription = null, tint = MaterialTheme.colors.primary)
                Spacer(modifier = Modifier.padding(start = 6.dp))
                Text(text = "按钮自定义样式")
            }
            Divider()

            OutlinedButton(
                modifier = Modifier.padding(16.dp),
                onClick = { channel.trySend("点击Button") }
            ) {
                Text(text = "OutlinedButton")
            }
            Divider()

            TextButton(
                modifier = Modifier.padding(16.dp),
                onClick = { channel.trySend("点击Button") }
            ) {
                Text(text = "TextButton")
            }
            Divider()

            IconButton(
                modifier = Modifier.padding(16.dp),
                onClick = { channel.trySend("点击IconButton") }
            ) {
                Icon(imageVector = Icons.Filled.Android, contentDescription = null, tint = MaterialTheme.colors.primary)
            }
            Divider()

            RadioButtonSample(channel)
            Divider()

            CheckboxSample()
            Divider()

            SwitchSample()
            Divider()

            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                backgroundColor = MaterialTheme.colors.primary,
                onClick = { channel.trySend("点击FloatingActionButton") }) {
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                    Text("FloatingActionButton", modifier = Modifier.align(Alignment.CenterVertically))
                }
            }
        }
    }
}

@Composable
private fun RadioButtonSample(channel: Channel<String>) {

    val list = remember { listOf("1", "2", "3") }
    val (selected, onSelectedChange) = remember { mutableStateOf<String>(list[0]) }

    Column(modifier = Modifier.selectableGroup()) {
        list.map {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(selected = it == selected, onClick = {
                        onSelectedChange(it)
                    })
            ) {
                RadioButton(
                    modifier = Modifier.padding(16.dp),
                    selected = selected == it,
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colors.primary,
                        unselectedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                        disabledColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                    )
                )
                Text(text = it, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }
    }
}

@Composable
fun CheckboxSample() {
    val (state, onStateChange) = remember { mutableStateOf(true) }
    val (state2, onStateChange2) = remember { mutableStateOf(true) }
    val parentState = remember(state, state2) {
        if (state && state2) ToggleableState.On
        else if (!state && !state2) ToggleableState.Off
        else ToggleableState.Indeterminate
    }

    val onParentClick = {
        val s = parentState != ToggleableState.On
        onStateChange(s)
        onStateChange2(s)
    }

    TriStateCheckbox(
        modifier = Modifier.padding(start = 16.dp),
        state = parentState,
        onClick = onParentClick,
        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
    )
    Spacer(modifier = Modifier.size(25.dp))
    Column(modifier = Modifier.padding(start = 26.dp)) {
        Checkbox(checked = state, onCheckedChange = onStateChange)
        Spacer(modifier = Modifier.size(25.dp))
        Checkbox(checked = state2, onCheckedChange = onStateChange2)
    }
}

@Composable
fun SwitchSample() {
    val (checkedState, onCheckedStateChange) = remember { mutableStateOf(true) }
    Switch(
        checked = checkedState,
        onCheckedChange = onCheckedStateChange,
        modifier = Modifier.padding(16.dp),
        colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colors.primary)
    )
}

@Preview
@Composable
fun PreviewButtonPage() {
    ButtonPage(naviController = rememberNavController())
}