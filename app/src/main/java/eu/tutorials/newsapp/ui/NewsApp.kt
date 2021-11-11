package eu.tutorials.newsapp.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.tutorials.newsapp.BottomMenuScreen
import eu.tutorials.newsapp.MockData
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
        Navigation(navController =navController , scrollState =scrollState )
    }
}

@Composable
fun Navigation(navController:NavHostController,scrollState: ScrollState,newsManager: NewsManager= NewsManager()) {

    val articles = newsManager.newsResponse.value.articles
    Log.d("newss","$articles")
    articles?.let {
    NavHost(navController = navController, startDestination =BottomMenuScreen.TopNews.route) {
        //Todo 7:pass articles to bottomNavigation
        bottomNavigation(navController = navController, articles)
        composable("Detail/{newsId}",
            arguments = listOf(
                navArgument("newsId") { type = NavType.IntType }
            )) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("newsId")
            val newsData = MockData.getNews(id)
            DetailScreen(newsData, scrollState, navController)
        }
    }
    }
}

//Todo 6: create TopNews list and provide the value to TopNews composable
fun NavGraphBuilder.bottomNavigation(navController: NavController,articles:List<TopNewsArticle>) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController,articles)
    }
    composable(BottomMenuScreen.Categories.route) {
        Categories()
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources()
    }
}