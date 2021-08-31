package me.fengmlo.composetest.ui.textfield

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import me.fengmlo.composetest.ui.common.AppTopBar

@Composable
fun TextFieldPage(navController: NavHostController) {

    val snackbarHostState = remember { SnackbarHostState() }
    val channel = remember { Channel<String>(capacity = Channel.CONFLATED) }
    val focusRequester2 = remember { FocusRequester() }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(channel) {
        channel.receiveAsFlow().collect {
            snackbarHostState.showSnackbar(message = it)
        }
    }

    ProvideWindowInsets {
        Scaffold(
            topBar = {
                AppTopBar(navController, title = "TextField")
            },
            scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)
        ) {

            Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
                SimpleFilledTextField(
                    modifier = Modifier
                        .padding(16.dp)
                        .focusOrder { }
                        .focusRequester(focusRequester = focusRequester),
                    showMessage = {
                        channel.trySend(it)
                    },
                    onClickNext = {
                        focusRequester2.requestFocus()
                    }
                )
                Divider()

                SideEffect {
                    focusRequester.requestFocus()
                }

                SimplePasswordTextField(
                    modifier = Modifier
                        .padding(16.dp)
                        .focusRequester(focusRequester = focusRequester2),
                    showMessage = {
                        channel.trySend(it)
                    })
                Divider()

                SearchTextField(modifier = Modifier.padding(16.dp), showMessage = {
                    channel.trySend(it)
                })
                Divider()

                SimpleOutlinedTextField(modifier = Modifier.padding(16.dp))
                Divider()

                SimpleBasicTextField(
                    modifier = Modifier
//                        .border(width = 1.dp, color = Color.LightGray, shape = CircleShape)
                        .padding(8.dp) // 这里加Padding对内容和边框间距无效
                )
            }

        }
    }

}

@Composable
fun SimpleFilledTextField(modifier: Modifier = Modifier, showMessage: (String) -> Unit, onClickNext: () -> Unit) {
    val (text, onTextChange) = remember { mutableStateOf(TextFieldValue(text = "Hello", selection = TextRange("Hello".length, "Hello".length))) }
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = onTextChange,
        label = { Text("Label") },
//        enabled = false,
//        readOnly = true,
        textStyle = TextStyle(
            fontSize = 20.sp,
        ),
        leadingIcon = { Icon(imageVector = Icons.Filled.Mail, contentDescription = null) },
        trailingIcon = { Icon(imageVector = Icons.Filled.HideImage, contentDescription = null) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters,
            autoCorrect = true,
//            keyboardType = KeyboardType.NumberPassword,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                showMessage("onDone")
            },
            onGo = {
                showMessage("onGo")
            },
            onNext = {
                showMessage("onNext")
                onClickNext()
            },
            onPrevious = {
                showMessage("onPrevious")
            },
            onSearch = {
                showMessage("onSearch")
            },
            onSend = {
                showMessage("onSend")
            }
        ),
        singleLine = true,
//        maxLines = 2,
//        interactionSource = remember { MutableInteractionSource() },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Blue,
            cursorColor = Color.Red,
            placeholderColor = Color.Yellow
        ),
        placeholder = {
            Text(text = "This is hint")
        },
    )
}

@Composable
fun SimplePasswordTextField(modifier: Modifier = Modifier, showMessage: (String) -> Unit) {
    val (text, onTextChange) = remember { mutableStateOf(TextFieldValue(text = "Hello", selection = TextRange("Hello".length, "Hello".length))) }
    val (showPassword, onShowPasswordChanged) = remember { mutableStateOf(false) }
    val maxLength = 6

    TextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = {
            if (it.text.length <= maxLength) onTextChange(it) else {
                showMessage("最大长度6")
            }
        },
        label = { Text("Password") },
//        enabled = false,
//        readOnly = true,
        textStyle = TextStyle(
            fontSize = 20.sp,
        ),
        leadingIcon = { Icon(imageVector = Icons.Filled.Lock, contentDescription = null) },
        trailingIcon = {
            Icon(
                imageVector = if (showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                contentDescription = null,
                modifier = Modifier.clickable {
                    onShowPasswordChanged(!showPassword)
                }
            )
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters,
            autoCorrect = true,
//            keyboardType = KeyboardType.NumberPassword,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                showMessage("onDone")
            },
            onGo = {
                showMessage("onGo")
            },
            onNext = {
                showMessage("onNext")
            },
            onPrevious = {
                showMessage("onPrevious")
            },
            onSearch = {
                showMessage("onSearch")
            },
            onSend = {
                showMessage("onSend")
            }
        ),
        singleLine = true,
        maxLines = 2,
//        interactionSource = remember { MutableInteractionSource() },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Blue,
            cursorColor = Color.Red,
            placeholderColor = Color.Yellow
        ),
        placeholder = {
            Text(text = "This is hint")
        },
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun SearchTextField(modifier: Modifier = Modifier, showMessage: (String) -> Unit) {
    val (text, onTextChange) = remember { mutableStateOf("Hello") }
    val maxLength = 20

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(width = 1.dp, color = Color.LightGray, shape = CircleShape),
        value = text,
        onValueChange = {
            if (it.length <= maxLength) onTextChange(it) else {
                showMessage("最大长度$maxLength")
            }
        },
        trailingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
        textStyle = TextStyle(
            fontSize = 16.sp,
        ),
        keyboardOptions = KeyboardOptions(
            autoCorrect = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                showMessage("onDone")
            },
            onGo = {
                showMessage("onGo")
            },
            onNext = {
                showMessage("onNext")
            },
            onPrevious = {
                showMessage("onPrevious")
            },
            onSearch = {
                showMessage("onSearch")
            },
            onSend = {
                showMessage("onSend")
            }
        ),
        singleLine = true,
        shape = CircleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Blue,
            cursorColor = Color.Red,
            placeholderColor = Color.Gray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            backgroundColor = Color.White
        ),
        placeholder = {
            Text(text = "请输入要搜索的内容")
        },
    )
}

@Composable
fun SimpleOutlinedTextField(modifier: Modifier = Modifier) {
    val (text, onTextChange) = remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = onTextChange,
        label = { Text("Label") }
    )
}

@Composable
fun SimpleBasicTextField(modifier: Modifier = Modifier) {
    val (text, onTextChange) = remember { mutableStateOf("Basic Text Field") }
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier,
        cursorBrush = SolidColor(Color.Blue),
        decorationBox = { innerTextField ->
            Surface(
                color = Color.White,
//                modifier = Modifier.padding(8.dp), // 这里加Padding对内容无效
                shape = CircleShape, border = BorderStroke(1.dp, color = Color.LightGray)
            ) {
                Box(modifier = Modifier.padding(8.dp)) {
                    innerTextField()
                }
            }
        }
    )
}


@Preview
@Composable
fun PreviewTextFieldPage() {
    TextFieldPage(navController = rememberNavController())
}


