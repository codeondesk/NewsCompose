package eu.tutorials.newsapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydoves.landscapist.coil.CoilImage
import eu.tutorials.newsapp.model.MockData
import eu.tutorials.newsapp.model.MockData.getTimeAgo
import eu.tutorials.newsapp.R
import eu.tutorials.newsapp.components.SearchBar
import eu.tutorials.newsapp.network.models.TopNewsArticle

//Todo 3 create a query variable as a parameter
@Composable
fun TopNews(navController: NavController,articles:List<TopNewsArticle>,query: MutableState<String>) {
    Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally) {
      //Todo 4: replace Text tile with the SearchBar and pass in query as argument
        SearchBar(query = query)
            LazyColumn {
                items(articles.size) { index ->
                    TopNewsItem(article = articles[index],
                        onNewsClick = { navController.navigate("Detail/$index") }
                    )
                }
        }
    }
}

@Composable
fun TopNewsItem(article: TopNewsArticle,onNewsClick: () -> Unit = {},) {
    Box(modifier = Modifier
        .height(200.dp)
        .padding(8.dp)
        .clickable {
            onNewsClick()
        }) {
        CoilImage(
            imageModel = article.urlToImage,
            // Crop, Fit, Inside, FillHeight, FillWidth, None
            contentScale = ContentScale.Crop,
            error = ImageBitmap.imageResource(R.drawable.breaking_news),
            // shows a placeholder ImageBitmap when loading.
            placeHolder = ImageBitmap.imageResource(R.drawable.breaking_news)
        )
        Column(modifier = Modifier
            .wrapContentHeight()
            .padding(top = 16.dp, start = 16.dp),verticalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = MockData.stringToDate(article.publishedAt?:"2021-11-10T14:25:20Z").getTimeAgo(),
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = article.title?:"Not Available", color = Color.White, fontWeight = FontWeight.SemiBold,
            maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }}
}

@Preview(showBackground = true)
@Composable
fun TopNewsPreview() {
    TopNewsItem(  TopNewsArticle(
        author = "Namita Singh",
        title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
        description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
        publishedAt = "2021-11-04T04:42:40Z"
    ))
}