package eu.tutorials.newsapp.ui.screen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import eu.tutorials.newsapp.network.NewsManager

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

}
}