package eu.tutorials.newsapp.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
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

@Composable
fun NewsApp(mainViewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()

    MainScreen(navController = navController,scrollState,mainViewModel)
}

@Composable
fun MainScreen(navController: NavHostController,scrollState: ScrollState,mainViewModel: MainViewModel) {
    Scaffold(bottomBar ={
        BottomMenu(navController = navController)
    }) {
        Navigation(navController =navController , scrollState =scrollState,paddingValues = it,viewModel = mainViewModel )
    }
}

@Composable
fun Navigation(navController:NavHostController, scrollState: ScrollState, paddingValues: PaddingValues, viewModel: MainViewModel) {
    val articles = mutableListOf(TopNewsArticle())
    val topArticles = viewModel.newsResponse.collectAsState().value.articles
    articles.addAll(topArticles ?: listOf())
    NavHost(navController = navController, startDestination =BottomMenuScreen.TopNews.route,modifier = Modifier.padding(paddingValues)) {
        //Todo 22 create a queryState and set query value from viewmodel,pass in querystate into bottomNavigation
        val queryState =
            mutableStateOf(viewModel.query.value)
        bottomNavigation(navController = navController, articles,viewModel = viewModel,query = queryState)
        composable("Detail/{index}",
            arguments = listOf(
                navArgument("index") { type = NavType.IntType }
            )) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")
            index?.let {
                if (queryState.value != "") {
                    articles.clear()
                    //Todo 21 collect searchedNewsResponse from viewModel
                    articles.addAll(viewModel.searchedNewsResponse.collectAsState().value.articles?: listOf())
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

//Todo 19:create a query variable
fun NavGraphBuilder.bottomNavigation(navController: NavController, articles:List<TopNewsArticle>, query: MutableState<String>,
                                     viewModel: MainViewModel
) {
    composable(BottomMenuScreen.TopNews.route) {

        //Todo 20: replace newsManager with viewModel and pass in a query parameter
       TopNews(navController = navController,articles,query,viewModel = viewModel)
        }
    composable(BottomMenuScreen.Categories.route) {
        viewModel.getArticlesByCategory("business")
        viewModel.onSelectedCategoryChanged("business")
        Categories(viewModel = viewModel,onFetchCategory = {
            viewModel.onSelectedCategoryChanged(it)
        viewModel.getArticlesByCategory(it)
        })
    }
    composable(BottomMenuScreen.Sources.route) {
        //Todo 13: pass in viewmodel as argument
        Sources(viewModel = viewModel)
    }
}