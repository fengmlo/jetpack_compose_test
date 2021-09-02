package me.fengmlo.composetest.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import me.fengmlo.composetest.ui.common.AppTopBar

@ExperimentalComposeUiApi
@Composable
fun DialogPage(naviController: NavHostController) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { AppTopBar(navController = naviController, title = "Dialog") },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
//            .height(150.dp)
//            .verticalScroll(state = rememberScrollState()) // 放开这里的注释可以测试Popup跟随anchor运动
                .background(color = Color.LightGray)
        ) {
            val dialogOpen1 = remember { mutableStateOf(false) }
            val dialogOpen2 = remember { mutableStateOf(false) }
            val dialogOpen3 = remember { mutableStateOf(false) }
            val popupOpen1 = remember { mutableStateOf(false) }
            val popupOpen2 = remember { mutableStateOf(false) }
            val popupOpen3 = remember { mutableStateOf(false) }
            val dropdownState = remember { mutableStateOf(false) }

            // Dialog 的theme只能在themes.xml中指定
            DialogSample(
                dialogOpen = dialogOpen1,
                onDismissRequest = {
                    dialogOpen1.value = false
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(message = "Dialog Dismiss")
                    }
                }
            )

            AlertDialogSample(
                dialogOpen = dialogOpen2,
                onDismissRequest = {
                    dialogOpen2.value = false
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(message = "AlertDialog Dismiss")
                    }
                }
            )

            AlertDialogSample2(
                dialogOpen = dialogOpen3,
                onDismissRequest = {
                    dialogOpen3.value = false
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(message = "AlertDialog Dismiss")
                    }
                }
            )

            PopupSample(
                popupOpen = popupOpen1,
                onDismissRequest = {
                    popupOpen1.value = false
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(message = "Popup Dismiss")
                    }
                }
            )

            PopupSample2(
                popupOpen = popupOpen2,
                onDismissRequest = {
                    popupOpen2.value = false
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(message = "Popup Dismiss")
                    }
                }
            )

            PopupSample3(
                popupOpen = popupOpen3,
                onDismissRequest = {
                    popupOpen3.value = false
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(message = "Popup Dismiss")
                    }
                }
            )

            DropdownMenuSample(
                expanded = dropdownState,
                onDismissRequest = {
                    dropdownState.value = false
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(message = "Dropdown Dismiss")
                    }
                }
            )
        }

    }
}

@ExperimentalComposeUiApi
@Composable
private fun DialogSample(
    onDismissRequest: () -> Unit = { },
    dialogOpen: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    if (dialogOpen.value) {
        Dialog(
            onDismissRequest = onDismissRequest, // 这个只会在点击返回和dialog外面出发dismiss的时候才会调用
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                securePolicy = SecureFlagPolicy.SecureOn, // 不允许截图
                usePlatformDefaultWidth = true
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .background(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(10.dp))
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "这是Dialog")
                Button(onClick = { dialogOpen.value = false }) {
                    Text(text = "关闭")
                }
            }
        }
    }

    Button(modifier = Modifier.padding(16.dp), onClick = { dialogOpen.value = !dialogOpen.value }) {
        Text(if (!dialogOpen.value) "显示Dialog" else "隐藏Dialog")
    }
}

@ExperimentalComposeUiApi
@Composable
private fun AlertDialogSample(
    onDismissRequest: () -> Unit = { },
    dialogOpen: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    if (dialogOpen.value) {
        AlertDialog(
            onDismissRequest = onDismissRequest, // 这个只会在点击返回和dialog外面出发dismiss的时候才会调用
            buttons = {
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = { dialogOpen.value = false }) {
                        Text(text = "确定")
                    }
                    TextButton(onClick = { dialogOpen.value = false }) {
                        Text(text = "取消")
                    }
                }
            },
            title = {
                Text(text = "标题")
            },
            text = {
                Text(text = "这是Text")
            },
            shape = RoundedCornerShape(10.dp),
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                securePolicy = SecureFlagPolicy.SecureOn, // 不允许截图
                usePlatformDefaultWidth = true
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )
    }

    Button(modifier = Modifier.padding(16.dp), onClick = { dialogOpen.value = !dialogOpen.value }) {
        Text(if (!dialogOpen.value) "显示AlertDialog" else "隐藏AlertDialog")
    }
}

@ExperimentalComposeUiApi
@Composable
private fun AlertDialogSample2(
    onDismissRequest: () -> Unit = { },
    dialogOpen: MutableState<Boolean> = remember { mutableStateOf(false) }
) {

    if (dialogOpen.value) {
        AlertDialog(
            onDismissRequest = onDismissRequest, // 这个只会在点击返回和dialog外面出发dismiss的时候才会调用
            confirmButton = {
                TextButton(onClick = { dialogOpen.value = false }) {
                    Text(text = "确定")
                }
            },
            dismissButton = {
                TextButton(onClick = { dialogOpen.value = false }) {
                    Text(text = "取消")
                }
            },
            title = {
                Text(text = "标题")
            },
            text = {
                Text(text = "这是Text")
            },
            shape = RoundedCornerShape(10.dp),
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                securePolicy = SecureFlagPolicy.SecureOn, // 不允许截图
                usePlatformDefaultWidth = true
            ),
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
        )
    }

    Button(modifier = Modifier.padding(16.dp), onClick = { dialogOpen.value = !dialogOpen.value }) {
        Text(if (!dialogOpen.value) "显示AlertDialog" else "隐藏AlertDialog")
    }
}

@ExperimentalComposeUiApi
@Composable
private fun PopupSample(
    onDismissRequest: () -> Unit = { },
    popupOpen: MutableState<Boolean> = remember { mutableStateOf(false) }
) {

    Button(modifier = Modifier.padding(16.dp), onClick = { popupOpen.value = !popupOpen.value }) {
        Text(if (!popupOpen.value) "显示固定位置Popup" else "隐藏Popup")
    }

    if (popupOpen.value) {
        Popup(
            onDismissRequest = onDismissRequest,
            alignment = Alignment.TopStart,
            offset = IntOffset(x = 100, y = 100),
            properties = PopupProperties(
                focusable = true,
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                securePolicy = SecureFlagPolicy.SecureOn,
                excludeFromSystemGesture = false,
                clippingEnabled = true
            )
        ) {

            Column(
                modifier = Modifier
                    .size(width = 200.dp, height = 300.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Green, Color.Yellow
                            ),
                            start = Offset.Zero,
                            end = Offset.Infinite,
                            tileMode = TileMode.Repeated
                        ), shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Text("这是标题")
                Text(text = "这是Text")
            }

        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun PopupSample2(
    onDismissRequest: () -> Unit = { },
    popupOpen: MutableState<Boolean> = remember { mutableStateOf(false) }
) {

    val buttonSize = remember { mutableStateOf(IntSize(0, 0)) }
    val buttonPosition = remember { mutableStateOf(Offset(0f, 0f)) }

    if (popupOpen.value) {
        val popupPositionProvider = remember {
            object : PopupPositionProvider {
                override fun calculatePosition(
                    anchorBounds: IntRect, // 默认的anchorBounds为父布局在window中的位置
                    windowSize: IntSize,
                    layoutDirection: LayoutDirection,
                    popupContentSize: IntSize
                ): IntOffset {

//                    return IntOffset(x = anchorBounds.right, y = anchorBounds.bottom) // 这行代码会让popup显示在Column外面，因为anchorBounds是从Popup的父布局的layout位置获取的
                    return IntOffset(
                        x = (buttonPosition.value.x + buttonSize.value.width / 2f).toInt(),
                        y = (buttonPosition.value.y + buttonSize.value.height / 2f).toInt()
                    )
                }
            }
        }

        Popup(
            onDismissRequest = onDismissRequest,
            popupPositionProvider = popupPositionProvider,
//            properties = PopupProperties( // 这组设置可以让popup跟着anchor一起动
//                focusable = false,
//                dismissOnBackPress = true,
//                dismissOnClickOutside = false,
//                securePolicy = SecureFlagPolicy.SecureOn,
//                excludeFromSystemGesture = false,
//                clippingEnabled = true
//            )
            properties = PopupProperties(
                focusable = true,
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                securePolicy = SecureFlagPolicy.SecureOn,
                excludeFromSystemGesture = false,
                clippingEnabled = true
            )
        ) {

            Column(
                modifier = Modifier
                    .size(width = 200.dp, height = 300.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Green, Color.Yellow
                            ),
                            start = Offset.Zero,
                            end = Offset.Infinite,
                            tileMode = TileMode.Repeated
                        ), shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Text("这是标题")
                Text(text = "这是Text")
            }

        }
    }

    Button(
        modifier = Modifier
            .padding(16.dp)
            .onGloballyPositioned {
                buttonSize.value = it.size
                buttonPosition.value = it.positionInWindow()
            },
        onClick = { popupOpen.value = !popupOpen.value }
    ) {
        Text(if (!popupOpen.value) "显示相对位置Popup" else "隐藏Popup")
    }
}

@ExperimentalComposeUiApi
@Composable
private fun PopupSample3(
    onDismissRequest: () -> Unit = { },
    popupOpen: MutableState<Boolean> = remember { mutableStateOf(false) }
) {

    // Popup的父布局是它的anchor
    Button(
        modifier = Modifier.padding(16.dp),
        onClick = { popupOpen.value = !popupOpen.value }
    ) {
        Text(if (!popupOpen.value) "显示相对位置Popup, 父布局是它的Anchor" else "隐藏Popup")

        if (popupOpen.value) {
            val popupPositionProvider = remember {
                object : PopupPositionProvider {
                    override fun calculatePosition(
                        anchorBounds: IntRect, // 默认的anchorBounds为父布局在window中的位置
                        windowSize: IntSize,
                        layoutDirection: LayoutDirection,
                        popupContentSize: IntSize
                    ): IntOffset {

                        return IntOffset(
                            x = (anchorBounds.left + anchorBounds.width / 2f).toInt(),
                            y = (anchorBounds.top + anchorBounds.height / 2f).toInt()
                        ) // 这行代码会让popup左上角显示在Button中心
                    }
                }
            }

            Popup(
                onDismissRequest = onDismissRequest,
                popupPositionProvider = popupPositionProvider,
                properties = PopupProperties(
                    focusable = true,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                    securePolicy = SecureFlagPolicy.SecureOn,
                    excludeFromSystemGesture = false,
                    clippingEnabled = true
                )
            ) {
                Column(
                    modifier = Modifier
                        .size(width = 200.dp, height = 300.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.Green, Color.Yellow
                                ),
                                start = Offset.Zero,
                                end = Offset.Infinite,
                                tileMode = TileMode.Repeated
                            ), shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    // 这里Text的样式会受Button样式的影响
                    Text("这是标题")
                    Text(text = "这是Text")
                }

            }
        }
    }

}

@ExperimentalComposeUiApi
@Composable
private fun DropdownMenuSample(
    onDismissRequest: () -> Unit = { },
    expanded: MutableState<Boolean> = remember { mutableStateOf(false) }
) {

    val list = remember { (0..5).toList() }
    val selected = remember { mutableStateOf(list[0]) }

    Row(
        modifier = Modifier
            .clickable {
                expanded.value = !expanded.value
            }
            .padding(16.dp),
    ) {
        Text(text = selected.value.toString())
        Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = onDismissRequest,
            offset = DpOffset(0.dp, 0.dp),
            properties = PopupProperties(
                focusable = true,
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                securePolicy = SecureFlagPolicy.Inherit,
                excludeFromSystemGesture = true,
                clippingEnabled = true,
                usePlatformDefaultWidth = false
            )
        ) {
            list.forEach {
                DropdownMenuItem(onClick = {
                    expanded.value = false
                    selected.value = it
                }) {
                    Text(text = it.toString())
                }
                Divider()
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun PreviewDialogPage() {
    DialogPage(naviController = rememberNavController())
}