package me.fengmlo.composetest.ui.text

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import me.fengmlo.composetest.R

@Composable
fun TextPage(navController: NavHostController) {
    val systemUiController = rememberSystemUiController()
    val colorPrimary = MaterialTheme.colors.primary
    val snackbarHostState = remember { SnackbarHostState() }
    val channel = remember { Channel<String>(Channel.CONFLATED) }

    SideEffect {
        systemUiController.setStatusBarColor(colorPrimary)
    }
    LaunchedEffect(channel) {
        channel.receiveAsFlow().collect {
            snackbarHostState.showSnackbar(message = it)
        }
    }
    ProvideWindowInsets {
        Scaffold(
            scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
            topBar = {
                TopAppBar(
                    modifier = Modifier.statusBarsPadding(),
                    title = {
                        Text(text = "Text")
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    },
                )
            }) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(text = "Normal Text")
                Divider()

                Text(text = "Text with font size 20, 字号20", fontSize = 20.sp)
                Divider()

                Text(text = "Text with Thin(W100) fontWeight, 字体粗细", fontSize = 20.sp, fontWeight = FontWeight.W100)
                Divider()
                Text(
                    text = "Text with ExtraLight(W200) fontWeight, 字体粗细",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W200
                )
                Divider()
                Text(text = "Text with Light(W300) fontWeight, 字体粗细", fontSize = 20.sp, fontWeight = FontWeight.W300)
                Divider()
                Text(text = "Text with Normal(W400) fontWeight, 字体粗细", fontSize = 20.sp, fontWeight = FontWeight.W400)
                Divider()
                Text(text = "Text with Medium(W500) fontWeight, 字体粗细", fontSize = 20.sp, fontWeight = FontWeight.W500)
                Divider()
                Text(text = "Text with SemiBold(W600) fontWeight, 字体粗细", fontSize = 20.sp, fontWeight = FontWeight.W600)
                Divider()
                Text(text = "Text with Bold(W700) fontWeight, 字体粗细", fontSize = 20.sp, fontWeight = FontWeight.W700)
                Divider()
                Text(
                    text = "Text with ExtraBold(W800) fontWeight, 字体粗细",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W800
                )
                Divider()
                Text(text = "Text with Black(W900) fontWeight, 字体粗细", fontSize = 20.sp, fontWeight = FontWeight.W900)
                Divider()

                Text(text = "Text with Cursive FontFamily, 字体", fontSize = 20.sp, fontFamily = FontFamily.Cursive)
                Divider()
                Text(text = "Text with Default FontFamily, 字体", fontSize = 20.sp, fontFamily = FontFamily.Default)
                Divider()
                Text(text = "Text with Monospace FontFamily, 字体", fontSize = 20.sp, fontFamily = FontFamily.Monospace)
                Divider()
                Text(text = "Text with SansSerif FontFamily, 字体", fontSize = 20.sp, fontFamily = FontFamily.SansSerif)
                Divider()
                Text(text = "Text with Serif FontFamily, 字体", fontSize = 20.sp, fontFamily = FontFamily.Serif)
                Divider()

                // 斜体需要手机字体家族支持
                Text(
                    text = "Text with Italic FontStyle, 斜体（需要字体家族支持）",
                    fontSize = 20.sp,
//                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic
                )
                Divider()

                Text(text = "Text with colorPrimary Color, 颜色", fontSize = 20.sp, color = colorPrimary)
                Divider()

                Text(text = "Text with 5sp letterSpacing, 字母间距", fontSize = 20.sp, letterSpacing = 5.sp)
                Divider()

                Text(
                    text = "Text with LineThrough TextDecoration, 字体装饰",
                    fontSize = 20.sp,
                    textDecoration = TextDecoration.LineThrough
                )
                Divider()

                Text(
                    text = "Text with Underline TextDecoration, 字体装饰",
                    fontSize = 20.sp,
                    textDecoration = TextDecoration.Underline
                )
                Divider()

                Text(
                    text = "Text with LineThrough & Underline TextDecoration, 字体装饰",
                    fontSize = 20.sp,
                    textDecoration = TextDecoration.combine(
                        listOf(TextDecoration.LineThrough, TextDecoration.Underline)
                    )
                )
                Divider()

                Text(
                    text = "Text with Center TextAlign long long long long long text, 字体对齐",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Divider()
                Text(
                    text = "Text with Left TextAlign long long long long long text, 字体对齐",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Left
                )
                Divider()
                Text(
                    text = "Text with Justify TextAlign long long long long long text, 字体对齐",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Justify
                )
                Divider()

                Text(
                    text = "Text with 2em lineHeight long long long long long text, 行高",
                    fontSize = 20.sp,
                    lineHeight = 2.em
                )
                Divider()

                // 字体超过范围的设置要生效，要么限定字体的最大行数，要么限定高度并指定不能换行
                Text(
//                    modifier = Modifier.height(30.dp),
                    text = "Text with Ellipsis TextOverflow long long long long long text, 超过范围的效果",
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis,
//                    softWrap = false
                    maxLines = 1
                )
                Divider()

                Text(
                    modifier = Modifier
                        .width(200.dp)
                        .border(width = 1.dp, color = Color.Gray),
                    text = "Text with Visible TextOverflow long long long long long text, 超过范围的效果",
                    fontSize = 20.sp,
                    overflow = TextOverflow.Visible,
                    softWrap = false
                )
                Divider()


                Text(
                    text = "Text with TextStyle long long long long long text, 自定义样式",
                    style = TextStyle(
                        color = Color.Red,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        fontStyle = FontStyle.Italic,
                        fontSynthesis = FontSynthesis.All,
                        baselineShift = BaselineShift.Superscript,
                        background = Color.Yellow,
                        shadow = Shadow(color = Color.Blue, offset = Offset(x = 1f, y = 2f), blurRadius = 2f),
                        textDirection = TextDirection.ContentOrRtl,
                        textIndent = TextIndent(firstLine = 2.em, restLine = 0.em)
                    )
                )
                Divider()

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Blue, fontSize = 20.sp)) {
                            append("Text with Span ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.Red,
                                fontStyle = FontStyle.Italic,
                                fontSize = 25.sp
                            )
                        ) {
                            append("long long long long long text")
                        }
                        append(", 富文本")
                    })
                Divider()

                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = ParagraphStyle(
                                textIndent = TextIndent(firstLine = 2.em),
                                lineHeight = 2.em
                            )
                        ) {
                            withStyle(style = SpanStyle(color = Color.Blue, fontSize = 20.sp)) {
                                append("Text with Span \n")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 25.sp
                                )
                            ) {
                                append("long long long long long long long long long long text\n")
                            }
                            append(", 富文本")
                        }
                    })
                Divider()

                SelectionContainer {
                    Column {
                        Text("This text is selectable", fontSize = 20.sp)
                        Text("This one too", fontSize = 20.sp)
                        Text("This one as well", fontSize = 20.sp)
                        DisableSelection {
                            Text("But not this one", fontSize = 20.sp)
                            Text("Neither this one", fontSize = 20.sp)
                        }
                        Text("But again, you can select this one", fontSize = 20.sp)
                        Text("And this one too", fontSize = 20.sp)
                    }
                }
                Divider()

                ClickableText(
                    text = AnnotatedString(
                        "Click Me Click Me Click Me Click Me Click Me Click Me Click Me Click Me Click Me Click Me Click Me",
                        spanStyle = SpanStyle(fontSize = 20.sp)
                    ),
                    onClick = { offset ->
                        Log.d("ClickableText", "$offset -th character is clicked.")
                        channel.trySend("$offset -th character is clicked.")
                    }
                )
                Divider()

                AnnotatedClickableText(onClick = { message ->
                    channel.trySend(message)
                })
                Divider()

                AnnotatedRichText(onClick = { message ->
                    channel.trySend(message)
                })
                Divider()

            }

        }
    }
}

@Composable
fun AnnotatedClickableText(onClick: (message: String) -> Unit) {
    val annotatedText = buildAnnotatedString {
        append("Click")
        pushStringAnnotation(tag = "URL", annotation = "https://developer.android.com")
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        ) {
            append("here")
        }
        pop()
    }

    ClickableText(text = annotatedText, onClick = { offset ->
        annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset).firstOrNull()?.let {
            onClick(it.item)
        }
    })
}

@Composable
fun AnnotatedRichText(onClick: (message: String) -> Unit) {
    val annotatedText = buildAnnotatedString {
        append("Click")
        pushStringAnnotation(tag = "URL", annotation = "https://developer.android.com")
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        ) {
            append("here")
        }
        pop()
        append(" with Image")
        appendInlineContent("image", alternateText = "image")
        append("after Image")
    }
    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }

//    ClickableText(text = annotatedText, onClick = { offset ->
//        annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset).firstOrNull()?.let {
//            onClick(it.item)
//        }
//    })

    val textStyle = TextStyle(fontSize = 20.sp)
    Text(
        modifier = Modifier.pointerInput(onClick) {
            detectTapGestures { pos ->
                layoutResult.value?.let { layoutResult ->
                    val position = layoutResult.getOffsetForPosition(pos)
                    annotatedText.getStringAnnotations(start = position, end = position).firstOrNull()?.let {
                        onClick(it.item)
                    }
                }
            }
        },
        text = annotatedText,
        style = textStyle,
        inlineContent = mapOf(
            "image" to InlineTextContent(
                placeholder = Placeholder(
                    width = /*textStyle.fontSize*/40.sp,
                    height = /*textStyle.fontSize*/40.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.AboveBaseline
                )
            ) {
                Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = null)
            }
        ),
        onTextLayout = { layoutResult.value = it })
}

@Preview
@Composable
fun PreviewTextPage() {
    TextPage(navController = rememberNavController())
}