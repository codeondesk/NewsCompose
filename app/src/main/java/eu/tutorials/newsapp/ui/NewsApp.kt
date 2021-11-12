package eu.tutorials.newsapp.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.tutorials.newsapp.BottomMenuScreen
import eu.tutorials.newsapp.components.BottomMenu
import eu.tutorials.newsapp.network.NewsManager
import eu.tutorials.newsapp.network.models.TopNewsArticle
import eu.tutorials.newsapp.ui.screen.Categories
import eu.tutorials.newsapp.ui.screen.DetailScreen
import eu.tutorials.newsapp.ui.screen.Sources
import eu.tutorials.newsapp.ui.screen.TopNews

@Composable
fun NewsApp() {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()

    MainScreen(navController = navController,scrollState)
}

@Composable
fun MainScreen(navController: NavHostController,scrollState: ScrollState) {
    Scaffold(bottomBar ={
        BottomMenu(navController = navController)
    }) {
        Navigation(navController =navController , scrollState =scrollState,paddingValues = it )
    }
}

@Composable
fun Navigation(navController:NavHostController,scrollState: ScrollState,newsManager: NewsManager= NewsManager(),paddingValues: PaddingValues) {
    val articles = mutableListOf(TopNewsArticle())
    articles.addAll(newsManager.newsResponse.value.articles ?: listOf(TopNewsArticle()))
    Log.d("newss","$articles")
    NavHost(navController = navController, startDestination =BottomMenuScreen.TopNews.route,modifier = Modifier.padding(paddingValues)) {
        //Todo 10: pass in newsManager value
        bottomNavigation(navController = navController, articles,newsManager)
        composable("Detail/{index}",
            arguments = listOf(
                navArgument("index") { type = NavType.IntType }
            )) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")
            index?.let {
                val article = articles[index]
                DetailScreen(article, scrollState, navController)
            }
        }
    }
}

/**Todo 10: create the newsManager variable, pass in the value to Categories and call onSelectedCategoryChanged
 * in onFetchedCategory block and pass in the emitted string.
 *
  */
fun NavGraphBuilder.bottomNavigation(navController: NavController,articles:List<TopNewsArticle>,
newsManager: NewsManager) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController,articles)
    }
    composable(BottomMenuScreen.Categories.route) {
        Categories(newsManager = newsManager,onFetchCategory = {newsManager.onSelectedCategoryChanged(it)})
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources()
    }
}