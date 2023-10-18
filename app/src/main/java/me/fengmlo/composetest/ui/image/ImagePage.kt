package me.fengmlo.composetest.ui.image

import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import me.fengmlo.composetest.R
import me.fengmlo.composetest.ui.common.AppTopBar

@Composable
fun ImagePage(navController: NavHostController) {
    Scaffold(topBar = { AppTopBar(navController = navController, title = "Image") }) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Image(
                modifier = Modifier.padding(16.dp),
                painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = null
            )

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .height(80.dp)
                    .width(80.dp)
                    .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { }
            )

            // 直接用 painterResource 加载 ic_launcher 会崩
            val drawable = AppCompatResources.getDrawable(LocalContext.current, R.mipmap.ic_launcher)
            Image(
                painter = rememberDrawablePainter(drawable = drawable), contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .height(80.dp)
                    .width(80.dp)
                    .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { }
            )

            AsyncImage(
                modifier = Modifier.size(200.dp).background(Color.Blue),
                model = "https://pic.616pic.com/bg_w1180/00/14/86/YCnYPJmxlf.jpg",
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

        }
    }
}

@Preview
@Composable
fun PreviewImagePage() {
    ImagePage(navController = rememberNavController())
}
