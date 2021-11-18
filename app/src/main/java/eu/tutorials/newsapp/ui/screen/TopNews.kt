package eu.tutorials.newsapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import eu.tutorials.newsapp.R
import eu.tutorials.newsapp.components.ErrorUI
import eu.tutorials.newsapp.components.LoadingUI
import eu.tutorials.newsapp.components.SearchBar
import eu.tutorials.newsapp.data.models.TopNewsArticle
import eu.tutorials.newsapp.model.MockData
import eu.tutorials.newsapp.model.MockData.getTimeAgo
import eu.tutorials.newsapp.ui.MainViewModel

//Todo 7: create the loading and error state as parameter
@Composable
fun TopNews(navController: NavController,articles:List<TopNewsArticle>,query: MutableState<String>,
           viewModel: MainViewModel,isLoading:MutableState<Boolean>,isError:MutableState<Boolean>
) {
    Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally) {
      SearchBar(query = query,viewModel = viewModel)
        val searchedText = query.value
        val resultList = mutableListOf<TopNewsArticle>()
        if (searchedText != "") {
            resultList.addAll(viewModel.searchedNewsResponse.collectAsState().value.articles?: articles)
        }else{
            resultList.addAll(articles)
        }
        //Todo 8: if state is loading show the loadingui, if there is an error show the errorui,
        // if article response is returned show lazy column and set the article
        when{
            isLoading.value->{
                LoadingUI()
            }
            isError.value->{
                ErrorUI()
            }
            else->{
                LazyColumn {
                    items(resultList.size) { index ->
                        TopNewsItem(article = resultList[index],
                            onNewsClick = { navController.navigate("Detail/$index") }
                        )
                    }
                }
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