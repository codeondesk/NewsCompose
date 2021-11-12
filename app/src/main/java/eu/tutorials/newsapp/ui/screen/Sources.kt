package eu.tutorials.newsapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.tutorials.newsapp.R
import eu.tutorials.newsapp.network.NewsManager
import eu.tutorials.newsapp.network.models.TopNewsArticle

/**
 * Todo 1: we create a newsManager variable, create a list for the drop down items
 * Then add a scaffold.
 * */
@Composable
fun Sources(newsManager: NewsManager) {
    val items = listOf(
       "TechCrunch" to "techcrunch",
        "TalkSport" to "talksport",
        "SABQ" to "sabq",
       "Reuters" to "reuters",
        "Politico" to "politico",
      "TheVerge" to "verge"
    )
    Scaffold(topBar={
        //TOdo 3: Paa in TopAppBar, set the title to the source name and add drop down as an actions
    TopAppBar(
        title = {
            Text(text = "${newsManager.sourceName.value} Source")
        },
        actions = {
            //Todo 4: we create a remember variable to control the show and dismisgs of the drop down
            var menuExpanded by remember { mutableStateOf(false) }

            IconButton(onClick = { menuExpanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = null)
            }
            MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = {
                        menuExpanded = false
                    },
                ) {
                    items.forEach {
                        DropdownMenuItem(onClick = {
                            newsManager.sourceName.value = it.second
                            menuExpanded = false
                        }) {
                            Text(it.first)
                        }
                    }
                }
            }
        }
    )}){

        newsManager.getArticleBySource()
        val article = newsManager.getArticleBySource.value
        //Todo 9: Add the Content composable and pass in articles by source
        SourceContent(articles = article.articles?: listOf() )

}
}

//Todo 8: Create a composable to displace the articles
@Composable
fun SourceContent(articles:List<TopNewsArticle>) {
    LazyColumn{
        items(articles) { article ->
            Card(backgroundColor = colorResource(id = R.color.purple_700),elevation = 6.dp, modifier = Modifier.padding(8.dp)) {
                Column(modifier = Modifier
                    .height(200.dp)
                    .padding(end = 8.dp, start = 8.dp),verticalArrangement = Arrangement.SpaceEvenly) {
                    Text(text = article.title ?: "Not Available",fontWeight = FontWeight.Bold,maxLines = 2,overflow = TextOverflow.Ellipsis)
                    Text(text = article.description ?: "Not Available",maxLines = 3,overflow = TextOverflow.Ellipsis)
                    Text(text =buildAnnotatedString {
                        pushStringAnnotation(tag = "article", annotation = article.url?:"Newsapi.org")
                        withStyle(style = SpanStyle(color = colorResource(id = R.color.hyper_link_color))) {
                            append("Read Full Article Here")
                        }
                        pop()
                    })
                }
            }
        }}}

@Preview(showBackground = true)
@Composable
fun SourceContentPreview() {
    SourceContent(articles = listOf(TopNewsArticle(
        author = "Namita Singh",
        title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
        description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
        publishedAt = "2021-11-04T04:42:40Z"
    )

    ))
}