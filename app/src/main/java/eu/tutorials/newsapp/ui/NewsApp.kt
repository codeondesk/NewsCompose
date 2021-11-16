package eu.tutorials.newsapp.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.tutorials.newsapp.BottomMenuScreen
import eu.tutorials.newsapp.components.BottomMenu
import eu.tutorials.newsapp.data.models.TopNewsArticle
import eu.tutorials.newsapp.data.network.Api
import eu.tutorials.newsapp.data.network.NewsManager
import eu.tutorials.newsapp.ui.screen.Categories
import eu.tutorials.newsapp.ui.screen.DetailScreen
import eu.tutorials.newsapp.ui.screen.Sources
import eu.tutorials.newsapp.ui.screen.TopNews

//Todo 19: create MainViewModel as parameter and pass into mainScreen
@Composable
fun NewsApp(mainViewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()

    MainScreen(navController = navController,scrollState,mainViewModel)
}
 //Todo 18 create MainViewModel as parameter and pass to Navigation
@Composable
fun MainScreen(navController: NavHostController,scrollState: ScrollState,mainViewModel: MainViewModel) {
    Scaffold(bottomBar ={
        BottomMenu(navController = navController)
    }) {
        Navigation(navController =navController , scrollState =scrollState,paddingValues = it,viewModel = mainViewModel )
    }
}

/**Todo 17: create MainViewModel as a parameter and collect newsResponse as state
 * get the topArticles and assign to  anew variable and then add to articles
 * */
@Composable
fun Navigation(navController:NavHostController, scrollState: ScrollState, newsManager: NewsManager = NewsManager(
    Api.retrofitService), paddingValues: PaddingValues, viewModel: MainViewModel) {
    val articles = mutableListOf(TopNewsArticle())
    val topArticles = viewModel.newsResponse.collectAsState().value.articles
    articles.addAll(topArticles ?: listOf())
    NavHost(navController = navController, startDestination =BottomMenuScreen.TopNews.route,modifier = Modifier.padding(paddingValues)) {
        bottomNavigation(navController = navController, articles,newsManager,viewModel = viewModel)
        composable("Detail/{index}",
            arguments = listOf(
                navArgument("index") { type = NavType.IntType }
            )) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")
            index?.let {
                if (newsManager.query.value.isNotEmpty()) {
                    articles.clear()
                    articles.addAll(newsManager.searchedNewsResponse.value.articles?: listOf())
                }else{
                    articles.clear()
                    articles.addAll(topArticles?: listOf())
                }
                val article = articles[index]
                DetailScreen(article, scrollState, navController)
            }
        }
    }
}

//Todo 31: create MainViewModel parameter
fun NavGraphBuilder.bottomNavigation(navController: NavController,articles:List<TopNewsArticle>,
newsManager: NewsManager,viewModel: MainViewModel
) {
    composable(BottomMenuScreen.TopNews.route) {
       TopNews(navController = navController,articles,newsManager.query,newsManager = newsManager)
        }
    composable(BottomMenuScreen.Categories.route) {
        //Todo 16: comment out getArticlesByCategory
        //Todo 32:getArticles and selectedCategory from viewModel
        viewModel.getArticlesByCategory("business")
        viewModel.onSelectedCategoryChanged("business")
        //Todo 33 replace newsManager with mainViewModel
        Categories(viewModel = viewModel,onFetchCategory = {
            viewModel.onSelectedCategoryChanged(it)
        viewModel.getArticlesByCategory(it)
        })
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources(newsManager = newsManager)
    }
}